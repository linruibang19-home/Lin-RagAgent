# Lin-RagAgent

**Lin-RagAgent** 是一个基于 Java 技术栈的 **企业级 AI 智能体对话平台**，在原有开源项目基础上经过架构重新设计与改编。

覆盖智能对话、文档知识问答、联网搜索、RAG 检索增强生成、MCP 工具协议、多执行器调度、Neo4j 图数据库结构图谱、文档全生命周期管理等完整能力。同时内置一套从基础到进阶的 **AI 技术示例模块**（`ai-example`），覆盖 Spring AI、Spring AI Alibaba、多向量库 RAG（PGVector / Milvus / Neo4j / ES）、会话记忆、MCP 协议等完整学习路径。

---

## 目录

- [项目架构](#项目架构)
- [核心能力](#核心能力)
- [对话流程设计](#对话流程设计)
- [RAG 检索链路](#rag-检索链路)
- [文档处理流水线](#文档处理流水线)
- [会话记忆策略](#会话记忆策略)
- [MCP 工具协议](#mcp-工具协议)
- [工程设计亮点](#工程设计亮点)
- [AI 示例模块](#ai-示例模块)
- [技术栈](#技术栈)
- [快速启动](#快速启动)

---

## 项目架构

```text
Lin-RagAgent/
├── Lin-RagAgent-business/                    # 业务核心层
│   └── Lin-RagAgent-business-chat/           # 对话与知识库核心业务
│       └── src/main/java/org/Lin/ai/
│           ├── auth/                         # JWT 认证授权
│           ├── chatagent/                    # 对话 Agent 核心
│           │   ├── rag/
│           │   │   ├── executor/             # 多执行器（歧义/RAG/ReAct/图谱）
│           │   │   ├── retrieve/channel/     # 双通道检索（向量+关键词）
│           │   │   └── model/                # 执行计划 & 证据模型
│           │   ├── support/                  # SSE流式/工具拦截/DashScope兼容
│           │   └── tool/                     # Agent 工具定义
│           ├── manage/                       # 文档管理
│           │   ├── config/                   # ES/Kafka/MinIO/Neo4j/PgVector 配置
│           │   ├── mq/                       # Kafka 异步消息处理
│           │   └── support/                  # 文档处理支撑（切块/解析/索引）
│           └── prompt/                       # Prompt 模板管理
├── Lin-RagAgent-common/                      # 通用公共层
│   ├── Lin-RagAgent-common-frame/            # 框架公共组件（异常/响应/填充）
│   └── Lin-RagAgent-common-web/              # Web 通用组件
├── Lin-RagAgent-id-generator-framework/      # 分布式 ID 生成器（Spring Boot Starter）
├── Lin-RagAgent-redisson-framework/          # Redisson 分布式工具框架
│   ├── Lin-RagAgent-redisson-service-framework/      # Redis 租约 & 分布式锁
│   └── Lin-RagAgent-service-delay-queue-framework/   # 延迟队列
├── ai-example/                               # AI 技术学习示例模块
│   ├── ai-example-one/                       # 入门基础示例
│   ├── ai-example-spring-ai/                 # Spring AI 核心用法
│   ├── ai-example-spring-ai-alibaba/         # Spring AI Alibaba & ReactAgent
│   ├── ai-example-memory/                    # 会话记忆三种策略示例
│   ├── ai-example-mcp/
│   │   ├── ai-example-spring-ai-office-mcp-server/   # MCP Server（Office 工具）
│   │   └── ai-example-spring-ai-office-mcp-client/   # MCP Client
│   └── ai-example-rag/
│       ├── ai-example-demo-rag/              # RAG 最简 Demo
│       ├── ai-example-spring-ai-rag/         # Spring AI RAG 基础
│       ├── ai-example-spring-ai-rag-pg/      # PGVector 向量检索
│       ├── ai-example-spring-ai-rag-pg-es/   # PG + ES 混合双通道检索
│       ├── ai-example-spring-ai-rag-milvus/  # Milvus 向量库
│       └── ai-example-spring-ai-rag-neo4j/   # Neo4j 图数据库 RAG
├── vue/                                      # 前端 Vue 3 + Vite
├── sql/
│   ├── Mysql/                                # MySQL 建库建表脚本
│   └── PostgresSql/                          # PostgreSQL 脚本
├── 需要的例子演示/                            # 示例文档资料（PDF/Markdown）
└── docker-compose.yml                        # Milvus + MinIO + etcd 环境编排
```

---

## 核心能力

### 多执行器调度体系

系统不会把所有问题直接交给模型，而是先经过前置编排器决策，再路由到最合适的执行器：

| 执行器 | 类名 | 触发场景 | 处理方式 |
| :--- | :--- | :--- | :--- |
| 歧义追问执行器 | `ClarificationExecutor` | 问题信息量不足 | 生成澄清问题引导用户补充 |
| RAG 知识问答执行器 | `RagChatExecutor` | 知识库可检索 | 证据驱动生成，来源可追溯 |
| 图谱纯导航执行器 | `GraphOnlyExecutor` | 文档结构定位 | 沿 Neo4j 图结构精准导航 |
| 图谱+证据混合执行器 | `GraphThenEvidenceExecutor` | 结构导航 + 内容检索 | 图定位 + 双通道检索组合 |
| ReAct Agent 执行器 | `ReactAgentExecutor` | 联网/多步推理 | 自主决策 + 工具调用循环 |

判断优先级：**歧义澄清 > 图谱导航 > RAG 知识问答 > ReAct Agent**

### 前置编排器五步决策

每次对话在进入执行器前，编排器按顺序完成：

1. **路由判定** — 判断问题类型（知识库 / 联网 / 歧义）
2. **问题改写** — 结合历史对话补全指代，还原完整语义
3. **子问题拆分** — 复合问题拆分（单轮最多 4 个子问题）
4. **知识域收缩** — 把检索范围从全库收窄到相关领域
5. **歧义检测** — 信息量不足时主动追问，不硬猜

最终产出 `ConversationExecutionPlan`（执行计划）交由对应执行器执行。

## 对话流程设计

### ReAct Agent 设计要点

- **模型调用限制**：`ModelCallLimitHook` 单次最多 8 次，会话累计最多 40 次
- **工具调用限制**：`ToolCallLimitHook` Tavily 搜索单次最多 6 次，会话累计最多 30 次
- **指数退避重试**：工具调用失败最多重试 2 次，初始延迟 200ms，最大 1200ms，带随机抖动
- **Checkpoint 持久化**：Spring AI Alibaba `MysqlSaver` 将 ReactAgent 状态存入 MySQL，重启后对话可恢复
- **并行工具执行**：`parallelToolExecution` 最多 4 个工具并行调用
- **DashScope 兼容**：`DashScopeCompatibilityInterceptor` 处理阿里云 API 协议差异

### 知识路由三级漏斗

用户提问后不直接全库检索，先经三级漏斗精确定位文档：**Scope（知识域）→ Topic（主题）→ Document（文档）**

通过语义 + 词法 + 关键词实体混合打分，置信度不足时主动降级。支持**影子路由**：用户手动选文档时，后台静默跑完整路由并记录命中率，用于持续优化路由质量。

---

## RAG 检索链路

### 双通道并行检索

- **向量通道**（`VectorRetrievalChannel`）：PGVector 语义相似度检索，设最低相似度阈值
- **关键词通道**（`KeywordRetrievalChannel`）：Elasticsearch 精确关键词检索，相对阈值过滤弱命中
- **RRF 融合**：两路排名通过 Reciprocal Rank Fusion 合并，不依赖分数量纲
- **可选 Rerank**：接入 SiliconFlow 兼容协议的外部精排服务，在候选集上二次排序

### Parent-Child 块聚合

检索阶段用 **Child 小块**保证命中精度；生成阶段自动聚合到 **Parent 大块**，保证上下文完整性。

### 证据预算控制

- 无有效证据时直接**短路返回**，不让模型凭空编造（从架构层面防幻觉）
- 多子问题时对证据总字符量做预算裁剪，防撑爆上下文窗口
- 模型回答要求标注引用来源编号 `[1][2]`，答案通过 SSE 实时流式推送，结束时补发引用来源和推荐追问问题

---

## 文档处理流水线

文档处理是完整的 Kafka 异步流水线，每步有独立任务日志和状态追踪（`SuperAgentDocumentTask` / `SuperAgentDocumentTaskLog`）：

1. **上传 & 存储** — 文件存入 MinIO，通过 Kafka 异步触发解析
2. **格式解析** — Apache Tika 统一解析 PDF / Word / PPT / Markdown 等格式，可配置知识域、业务分类、文档标签等元信息
3. **策略推荐** — 根据文档类型自动推荐最优切块组合，支持手动覆盖
4. **组合式切块引擎** — 四种策略各司其职：

   | 策略 | 角色 | 说明 |
   | :--- | :--- | :--- |
   | 结构切块 | 主干 | 按标题 / 章节 / 段落切成语义完整的块 |
   | 递归分块 | 兜底 | 结构块超大时继续裁剪，控制块大小 |
   | 语义分块 | 优化 | 在结构切块基础上做边界精修 |
   | LLM 智能切块 | 增强 | 处理低质量 / 复杂文档，默认关闭 |

5. **向量化 & 双引擎索引** — 向量写入 PGVector，关键词写入 Elasticsearch
6. **Neo4j 图谱构建** — 同步生成 `Document → Section → Item` 层级图结构，支持章节编号定位、邻接遍历

---

## 会话记忆策略

三种策略可按会话配置，持久化至 MySQL（`SuperAgentChatMemorySummary`）：

| 策略 | 场景 | 说明 |
| :--- | :--- | :--- |
| 无记忆 | 一次性查询 | 每轮独立，不携带历史 |
| 滑动窗口 | 短期连续追问 | 保留最近 N 轮完整对话 |
| 摘要压缩 | 长对话 / 生产环境 | 长期摘要 + 最近 4 轮原文，推荐方案 |

**摘要压缩细节**：最近 4 轮原文不压缩；更早历史增量摘要（单次推进最多 6 轮）；原文窗口上限 2200 字符，长期摘要上限 1400 字符。

---

## MCP 工具协议

基于 Model Context Protocol 标准协议，项目提供完整的 MCP Server / Client 示例（`ai-example-mcp`），生产业务层同样支持：

- **动态发现**：Agent 启动时自动扫描注册 MCP 工具，无需硬编码工具列表
- **双传输模式**：支持 Stdio 和 SSE，兼容主流 MCP Server 生态
- **参数自动提取**：模型根据意图自动识别工具并提取入参
- **多工具串联**：单次对话可串联调用多个 MCP 工具，前一工具输出作为后一工具输入
- **安全隔离**：工具调用有超时限制和异常隔离，防止影响主流程

---

## 工程设计亮点

### 分层架构

```text
business（AI 业务逻辑）
    ↓ 依赖
common（通用 Web / 框架组件）
    ↓ 依赖
framework（ID 生成器 / Redisson 工具，以 Spring Boot Starter 封装）
```

### 集群并发安全

- **`RedisLeaseManager`**：Redis 租约互斥，防止同一消息被多实例重复处理
- **`ChatRuntimeRegistry`**：JVM 级任务注册表，防止同进程重入
- 执行中自动续期，执行结束统一触发清理，不留孤儿锁
- **延迟队列框架**（`Lin-RagAgent-service-delay-queue-framework`）：支持定时任务调度

### 设计模式落地

| 模式 | 应用场景 |
| :--- | :--- |
| 策略模式 | 三种记忆策略、四种切块策略、多检索通道 |
| 工厂模式 | 检索通道创建、切块器创建 |
| 模板方法 | 文档处理流水线各节点 |
| 责任链 | RAG 前置编排器五步决策链 |
| 观察者 | SSE 流式输出回调 |
| AOP | 全链路 Trace、全局异常拦截 |

### 主要扩展点

- 新增检索通道：实现 `RetrievalChannel` 接口，注册 Bean 即可自动参与双通道
- 新增切块策略：实现切块接口，加入组合流水线任意位置
- 新增 MCP 工具：部署符合 MCP 协议的 Server，Agent 自动发现
- 新增记忆策略：实现记忆接口，配置即用

---

## AI 示例模块

`ai-example` 是独立于业务系统的学习模块，按技术深度递进排列：

| 模块 | 内容 |
| :--- | :--- |
| `ai-example-one` | Spring AI 入门：ChatClient / Prompt / 流式输出 |
| `ai-example-spring-ai` | Spring AI 核心：Function Call / Advisor / 结构化输出 |
| `ai-example-spring-ai-alibaba` | Spring AI Alibaba：DashScope 接入 / ReactAgent / Checkpoint |
| `ai-example-memory` | 三种会话记忆策略完整示例对比 |
| `ai-example-mcp` | MCP Server + Client 完整示例（Office 工具服务） |
| `ai-example-rag / demo-rag` | RAG 最简 Demo，理解核心流程 |
| `ai-example-rag / spring-ai-rag` | Spring AI RAG 基础 Pipeline |
| `ai-example-rag / rag-pg` | PGVector 向量检索 RAG |
| `ai-example-rag / rag-pg-es` | PGVector + ES 双通道混合检索 |
| `ai-example-rag / rag-milvus` | Milvus 向量库（`docker-compose` 一键启动） |
| `ai-example-rag / rag-neo4j` | Neo4j 图数据库驱动的 GraphRAG |

> `docker-compose.yml` 提供 Milvus + MinIO + Attu（可视化管理界面）一键启动环境。

---

## 技术栈

| 技术 | 版本 / 说明 |
| :--- | :--- |
| JDK | 17 |
| Spring Boot | 3.5.6 |
| Spring AI | 1.1.0 |
| Spring AI Alibaba | 1.1.2.0（ReactAgent / MysqlSaver） |
| MyBatis Plus | 3.5.7 |
| MySQL | 业务数据 / Agent Checkpoint / 记忆摘要 |
| PostgreSQL + PGVector | 向量存储 |
| Elasticsearch | 关键词倒排索引 |
| Neo4j | 文档结构图谱（Document → Section → Item） |
| Milvus | 向量库（ai-example 示例中使用） |
| Redis + Redisson | 分布式缓存 / 租约锁 |
| Kafka | 文档处理异步消息队列 |
| MinIO | 对象存储（文件上传） |
| Apache Tika | 多格式文档解析 |
| 阿里云 DashScope | 大模型服务（qwen 系列） |
| Tavily | 联网搜索工具 |
| SiliconFlow | Rerank 精排（可选） |
| Vue 3 + Vite | 前端 |
| Knife4j | Swagger 增强 API 文档 |
| Log4j2 | 日志框架 |
| JWT | 登录认证 |
| Hutool / Lombok | 工具类 |

---

## 快速启动

### 中间件一览

项目依赖的中间件分两类：**需手动安装部署**的核心服务，以及**通过 `docker-compose` 一键启动**的 Milvus 生态组件。

#### 手动安装（业务系统核心依赖）

| 中间件 | 版本建议 | 用途 | 默认连接地址 |
| :--- | :--- | :--- | :--- |
| MySQL | 8.x | 业务数据 / Agent Checkpoint / 会话记忆 | `127.0.0.1:3306` |
| PostgreSQL + pgvector | 15.x + pgvector 0.7+ | 向量存储（RAG 检索） | `127.0.0.1:5432` |
| Elasticsearch | 8.x | 关键词倒排索引 | `127.0.0.1:9200` |
| Redis | 7.x | 分布式锁 / 会话缓存 | `127.0.0.1:6379` |
| Kafka | 3.x | 文档异步处理消息队列 | `127.0.0.1:9092` |
| Neo4j | 5.x | 文档结构图谱（Document → Section → Item） | `bolt://127.0.0.1:7687` |

#### Docker Compose 一键启动（`docker-compose.yml`）

项目根目录的 `docker-compose.yml`（`name: milvus-stack`）包含以下 4 个服务：

| 服务 | 镜像 | 暴露端口 | 用途 |
| :--- | :--- | :--- | :--- |
| `etcd` | `quay.io/coreos/etcd:v3.5.18` | 内部（不对外） | Milvus 元数据存储 |
| `minio` | `minio/minio:RELEASE.2024-01-01T16-36-33Z` | `9000`（API）/ `9001`（控制台） | 对象存储，供 Milvus 持久化使用，同时作为**业务系统文件存储** |
| `standalone` | `milvusdb/milvus:v2.6.6` | `19530`（gRPC）/ `9091`（HTTP） | Milvus 向量数据库（`ai-example-rag-milvus` 使用） |
| `attu` | `zilliz/attu:v2.6.3` | `8000` → 容器 `3000` | Milvus 可视化管理界面 |

> **MinIO 说明**：docker-compose 中的 MinIO 实例（`127.0.0.1:9000`）可直接被业务系统复用，无需单独安装 MinIO。默认账号 `minioadmin` / `minioadmin` 与 `application.yaml` 中的配置一致。

启动命令：

```bash
docker-compose up -d
```

服务启动后可访问：

- MinIO 控制台：`http://localhost:9001`（账号 `minioadmin` / `minioadmin`）
- Attu（Milvus UI）：`http://localhost:8000`

---

### 环境变量

启动后端前，需要在系统环境变量（或 IDE Run Configuration）中配置以下变量：

| 变量名 | 说明 | 是否必须 |
| :--- | :--- | :--- |
| `ALI_BAI_LIAN_API_KEY` | 阿里云百炼 DashScope API Key（对话模型 + Embedding） | **必须** |
| `TAVILY_API_KEY` | Tavily 联网搜索 API Key（ReactAgent 联网能力） | **必须** |
| `ELASTICSEARCH_PASSWORD` | Elasticsearch 密码，默认 `elastic` | 可选 |
| `NEO4J_PASSWORD` | Neo4j 密码，默认 `12345678` | 可选 |
| `RERANK_API_KEY` | SiliconFlow Rerank API Key，未配置时回退到 `ALI_BAI_LIAN_API_KEY` | 可选 |
| `SUPER_AGENT_ADMIN_USERNAME` | 管理后台账号，默认 `admin` | 可选 |
| `SUPER_AGENT_ADMIN_PASSWORD` | 管理后台密码，默认 `admin123456` | 可选 |

PowerShell 快速设置示例：

```powershell
$env:ALI_BAI_LIAN_API_KEY = "sk-xxxxxxxxxxxx"
$env:TAVILY_API_KEY       = "tvly-xxxxxxxxxxxx"
```

---

### 启动步骤

#### 第一步：启动 Docker 组件（MinIO + Milvus）

```bash
docker-compose up -d
```

确认所有容器正常运行：

```bash
docker-compose ps
```

#### 第二步：初始化数据库

```bash
# MySQL 建库建表
mysql -u root -p < sql/Mysql/create_database_mysql.sql
mysql -u root -p < sql/Mysql/create_table_mysql.sql

# PostgreSQL 启用 pgvector 扩展（登录后执行）
# CREATE EXTENSION IF NOT EXISTS vector;
```

#### 第三步：配置环境变量

参考上方"环境变量"表，至少配置 `ALI_BAI_LIAN_API_KEY` 和 `TAVILY_API_KEY`。

#### 第四步：启动后端

```bash
mvn clean install -DskipTests
cd Lin-RagAgent-business/Lin-RagAgent-business-chat
mvn spring-boot:run
```

后端默认端口：`9082`，API 文档：`http://localhost:9082/doc.html`

#### 第五步：启动前端

```bash
cd vue
npm install
npm run dev
```

前端默认端口：`5173`，访问 `http://localhost:5173`

#### （可选）运行 AI 示例模块

各 `ai-example-*` 子模块均可独立启动：

```bash
cd ai-example/ai-example-rag/ai-example-spring-ai-rag-milvus
mvn spring-boot:run
```

> 示例文档资料位于 `需要的例子演示/` 目录，包含 PDF 产品手册和多份 Markdown 制度文档，可直接通过管理后台上传进行 RAG 测试。

---

License: Apache 2.0
