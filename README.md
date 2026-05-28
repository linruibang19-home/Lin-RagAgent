# Lin-RagAgent

**Lin-RagAgent** 是一个基于 Java 技术栈的 **企业级 AI 智能体对话平台**，结合 **VibeCoding** 理念进行全栈开发——以 AI 辅助驱动架构设计、模块拆解与代码实现，探索人机协同编程在复杂工程项目中的落地路径。

覆盖智能对话、文档知识问答、联网搜索、RAG 检索增强生成、MCP 工具协议、多执行器调度、Neo4j 图数据库结构图谱、文档全生命周期管理等完整业务能力。工程层面采用多模块 Maven 分层架构，前后端分离，后端以 Spring AI / Spring AI Alibaba 为核心驱动，支持向量库（PGVector / Milvus）+ 倒排索引（Elasticsearch）双通道检索。同时内置一套从基础到进阶的 **AI 技术示例模块**（`ai-example`），系统覆盖 Spring AI、多向量库 RAG、会话记忆、MCP 协议等完整学习路径，适合作为企业级 AI Agent 项目的参考实现与实战起点。

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

### 分层架构总览

```text
┌─────────────────────────────────────────────────────────────────────────┐
│                        前端展示层  vue/                                  │
│   Vue 3 · Vite · Vue Router                                             │
│   业务对话页（三种问答模式）│ 管理后台（文档 / 知识路由 / 对话观测）        │
└──────────────────────────────────┬──────────────────────────────────────┘
                                   │ HTTP REST / SSE 流式（端口 5173 → 代理）
┌──────────────────────────────────▼──────────────────────────────────────┐
│                   接入与安全层  Spring Boot（端口 9082）                  │
│   JWT 认证过滤器 │ 全局异常处理 │ 统一 ApiResponse 封装 │ 只读预览模式    │
└────────────┬─────────────────────────────────┬──────────────────────────┘
             │                                 │
┌────────────▼────────────────┐   ┌────────────▼────────────────────────┐
│     对话 Agent 模块          │   │       文档与知识管理模块              │
│                             │   │                                      │
│  前置编排器（5 步决策）       │   │  文档全生命周期：上传→解析→策略→索引  │
│  ├ 路由判定（知识库/联网）    │   │  ├ 行语义分类器                      │
│  ├ 问题改写（指代消歧）       │   │  ├ 结构层次树构建 & 歧义消解          │
│  ├ 子问题拆分（最多 4 个）    │   │  ├ Parent / Child 双粒度切块         │
│  ├ 知识路由三级漏斗           │   │  ├ AI 策略推荐（自动规划索引方案）    │
│  └ 歧义检测 & 主动追问        │   │  ├ 向量化写入 PGVector / ES          │
│                             │   │  ├ Neo4j 图谱节点与关系构建           │
│  执行器注册表                │   │  └ Kafka 异步队列解耦处理流程         │
│  ├ ClarificationExecutor    │   │                                      │
│  ├ RagChatExecutor          │   │  知识路由管理                         │
│  ├ GraphOnlyExecutor        │   │  ├ Scope / Topic 三级漏斗配置         │
│  ├ GraphThenEvidenceExecutor│   │  ├ 文档画像生成（AI 自动提取特征）     │
│  └ ReactAgentExecutor       │   │  ├ 主题文档关联绑定                   │
│                             │   │  └ 路由命中率追踪（影子路由）          │
│  RAG 检索引擎                │   └──────────────────────────────────────┘
│  ├ VectorRetrievalChannel   │
│  ├ KeywordRetrievalChannel  │
│  ├ RRF 融合排序              │
│  └ 证据预算裁剪 & 防幻觉短路 │
│                             │
│  Agent 工具层                │
│  ├ Tavily 联网搜索           │
│  └ 自定义工具（可扩展）       │
└─────────────────────────────┘
             │
┌────────────▼────────────────────────────────────────────────────────────┐
│                       基础框架 & 工具层                                  │
│  分布式雪花 ID（Starter）│ Redisson 分布式锁 │ Redis 延迟队列             │
│  全局异常处理 │ ApiResponse 封装 │ Web 拦截器基类 │ Prompt 模板管理       │
└────────────┬────────────────────────────────────────────────────────────┘
             │
┌────────────▼────────────────────────────────────────────────────────────┐
│                        中间件 / 存储层                                   │
│  MySQL         · 业务主库 + Spring AI Checkpoint（ReAct 状态持久化）     │
│  PostgreSQL    · pgvector 语义向量索引（RAG 检索主力）                   │
│  Elasticsearch · 关键词倒排索引（BM25 召回，双通道之一）                 │
│  Redis         · 分布式锁 / 会话租约 / 延迟任务                         │
│  Kafka         · 文档解析 & 索引构建任务异步消息队列                     │
│  Neo4j         · Document → Section → Item 三层文档图谱                 │
│  MinIO         · 原始文件存储（PDF / Markdown / TXT）                   │
│  Milvus        · 高性能向量数据库（ai-example 示例专用）                 │
└─────────────────────────────────────────────────────────────────────────┘
```

### 完整目录结构

```text
Lin-RagAgent/
├── Lin-RagAgent-business/                              # 业务核心层
│   └── Lin-RagAgent-business-chat/                    # 核心服务，端口 9082
│       └── src/main/java/org/Lin/ai/
│           ├── auth/                                  # 认证授权模块
│           │   ├── config/                            # JWT 过滤器 & 只读预览模式属性（PreviewModeProperties）
│           │   ├── controller/                        # 登录 / 登出 / 当前用户（/admin/auth）
│           │   ├── service/                           # 认证逻辑 & 管理员 Profile 查询
│           │   └── support/                           # JWT 工具 / Token 解析 / 预览模式拦截器
│           ├── chatagent/                             # 对话 Agent 核心模块
│           │   ├── config/                            # ReAct 限流、模型、DashScope 配置
│           │   ├── controller/                        # 业务对话接口（/api/chat）
│           │   │   └── BusinessChatController         # 流式/会话/停止/检索观测/阶段基准接口
│           │   ├── data/                              # MyBatis 数据访问（会话/Exchange/路由追踪表）
│           │   ├── model/                             # 领域模型
│           │   │   ├── ConversationSession            # 会话快照（含 exchanges 列表）
│           │   │   ├── ConversationExchange           # 单轮对话（问/答/工具/引用/推荐）
│           │   │   └── StageBenchmark                 # 各阶段耗时基准
│           │   ├── service/                           # BusinessChatService 统一业务入口
│           │   ├── rag/                               # RAG 推理引擎
│           │   │   ├── executor/                      # 多执行器体系
│           │   │   │   ├── ConversationExecutor       # 执行器公共接口
│           │   │   │   ├── ConversationExecutorRegistry # 执行器注册表（按优先级路由）
│           │   │   │   ├── ClarificationExecutor      # 歧义追问：信息不足时生成澄清问题
│           │   │   │   ├── RagChatExecutor            # 标准 RAG：证据驱动生成，来源可追溯
│           │   │   │   ├── GraphOnlyExecutor          # Neo4j 图谱纯导航：文档结构精准定位
│           │   │   │   ├── GraphThenEvidenceExecutor  # 图谱定位 + 双通道证据混合检索
│           │   │   │   └── ReactAgentExecutor         # ReAct Agent：联网搜索 / 多步自主推理
│           │   │   ├── retrieve/channel/              # 双通道并行检索
│           │   │   │   ├── VectorRetrievalChannel     # PGVector 语义向量检索（相似度阈值过滤）
│           │   │   │   └── KeywordRetrievalChannel    # Elasticsearch 关键词检索（BM25 召回）
│           │   │   ├── model/                         # 执行计划 & 证据模型
│           │   │   │   ├── ConversationExecutionPlan  # 编排器产出：执行器类型+子问题+路由结果
│           │   │   │   └── EvidencePackage            # 证据包：检索片段 + 来源元数据 + 预算控制
│           │   │   ├── service/                       # 前置编排器 / 知识路由服务 / 记忆摘要服务
│           │   │   └── support/                       # RRF 融合排序 / 证据预算裁剪工具
│           │   ├── support/                           # SSE 流式推送 / DashScope 协议兼容拦截器
│           │   └── tool/                              # Agent 工具（Tavily 联网搜索等）
│           ├── manage/                                # 文档与知识管理模块
│           │   ├── config/                            # ES / Kafka / MinIO / Neo4j / PgVector 连接配置
│           │   ├── controller/                        # REST 接口
│           │   │   ├── DocumentManageController       # 上传/分页/详情/删除/策略/索引/Chunk（/manage/document）
│           │   │   └── KnowledgeManageController      # Scope/Topic/画像/关联/路由追踪（/manage/knowledge）
│           │   ├── data/                              # 文档 / Chunk / Topic / Scope / 画像 数据层
│           │   ├── model/                             # 文档领域模型（Document / Chunk / Profile / Topic / Scope）
│           │   ├── mq/                                # Kafka 消息
│           │   │   ├── DocumentIndexTaskProducer      # 索引任务消息生产者
│           │   │   └── DocumentIndexTaskConsumer      # 索引任务消息消费者（异步执行解析+入库）
│           │   ├── service/                           # 文档服务群
│           │   │   ├── DocumentManageService          # 上传 / 策略推荐 / 确认 / 索引构建 / Chunk查询
│           │   │   └── KnowledgeManageService         # 路由漏斗配置 / 画像生成 / 关联管理 / 追踪分页
│           │   └── support/                           # 文档结构智能解析引擎
│           │       ├── DocumentLineClassifier         # 行语义分类（标题/代码块/表格/列表/正文）
│           │       ├── DocumentStructureSignalExtractor # 层级信号批量提取（缩进/序号/标点规则）
│           │       ├── DocumentStructureHierarchyResolver # 多层嵌套层次树还原
│           │       ├── DocumentStructureAmbiguityResolver # 标题与段落歧义消解
│           │       └── DocumentStructureTreeValidator  # 树结构合法性校验（孤节点/深度/循环检测）
│           └── prompt/                                # Prompt 模板统一管理（各阶段 Prompt 集中维护）
├── Lin-RagAgent-common/                               # 通用公共层
│   ├── Lin-RagAgent-common-frame/                     # 框架公共组件
│   │   ├── ApiResponse<T>                             # 统一响应封装（code=0 成功，-1/-100 错误）
│   │   ├── BaseException / SuperAgentFrameException   # 自定义异常体系（code + message）
│   │   └── DefaultExceptionHandler                    # 全局异常处理器（@RestControllerAdvice）
│   └── Lin-RagAgent-common-web/                       # Web 通用组件（字段填充器 / 拦截器基类）
├── Lin-RagAgent-id-generator-framework/               # 分布式 ID 生成器（Spring Boot Starter）
│   └── 基于雪花算法，自动分配 workerId，支持多实例部署唯一 ID 生成
├── Lin-RagAgent-redisson-framework/                   # Redisson 分布式工具框架
│   ├── Lin-RagAgent-redisson-service-framework/       # Redis 租约管理 & 分布式锁（RedisLeaseManager）
│   └── Lin-RagAgent-service-delay-queue-framework/    # 基于 Redis ZSet 的延迟队列（任务重试/降级）
├── ai-example/                                        # AI 技术学习示例模块（可独立运行）
│   ├── ai-example-one/                               # 入门：Chat / Prompt / 流式输出
│   ├── ai-example-spring-ai/                         # Spring AI 核心用法（Tool Call / Advisor / Memory）
│   ├── ai-example-spring-ai-alibaba/                 # Spring AI Alibaba & ReAct Agent 完整示例
│   ├── ai-example-memory/                            # 会话记忆三种策略（内存 / JDBC / 摘要压缩）
│   ├── ai-example-mcp/
│   │   ├── ai-example-spring-ai-office-mcp-server/   # MCP Server：Office 文件读写工具集
│   │   └── ai-example-spring-ai-office-mcp-client/   # MCP Client：连接 Server 发起工具调用
│   └── ai-example-rag/
│       ├── ai-example-demo-rag/                      # RAG 最简 Demo（存 / 检 / 问 三步）
│       ├── ai-example-spring-ai-rag/                 # Spring AI RAG 基础用法
│       ├── ai-example-spring-ai-rag-pg/              # PGVector 语义向量检索 RAG
│       ├── ai-example-spring-ai-rag-pg-es/           # PGVector + Elasticsearch 混合双通道检索
│       ├── ai-example-spring-ai-rag-milvus/          # Milvus 向量库集成
│       └── ai-example-spring-ai-rag-neo4j/           # Neo4j 图数据库 RAG
├── vue/                                              # 前端（Vue 3 + Vite）
│   └── src/
│       ├── views/BusinessChatView.vue                # 业务对话主页（三模式切换 + 文档选择框）
│       ├── views/admin/                              # 管理后台（文档接入 / 知识路由 / 对话观测）
│       ├── api/api.js                                # 全量 API 封装（fetch + SSE 流式）
│       └── utils/knowledgeRoute.js                   # 知识路由结果展示工具
├── sql/
│   ├── Mysql/                                        # MySQL 建库 & 建表脚本
│   └── PostgresSql/                                  # pgvector 扩展初始化脚本
├── 需要的例子演示/                                    # 示例文档资料（PDF / Markdown）
└── docker-compose.yml                               # 全量中间件编排（10 个服务一键启动）
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

项目根目录的 `docker-compose.yml`（`name: lin-ragagent-stack`）包含**全部 10 个中间件服务**，一条命令即可启动所有依赖，无需手动安装：

| 服务 | 镜像 | 端口 | 用途 | 账号/密码 |
| :--- | :--- | :--- | :--- | :--- |
| `mysql` | `mysql:8.0` | `3306` | 业务数据 / Checkpoint / 记忆 | `root` / `root` |
| `postgres` | `pgvector/pgvector:pg15` | `5432` | 向量存储（RAG 检索） | `postgres` / `postgres` |
| `elasticsearch` | `elasticsearch:8.13.0` | `9200` | 关键词倒排索引，安全认证已关闭 | 无需认证 |
| `redis` | `redis:7.2` | `6379` | 分布式锁 / 缓存 | 无密码 |
| `kafka` | `bitnami/kafka:3.7` | `9092` | 文档异步处理（KRaft 模式） | 无认证 |
| `neo4j` | `neo4j:5.20` | `7474` / `7687` | 文档结构图谱 | `neo4j` / `12345678` |
| `minio` | `minio/minio` | `9000` / `9001` | 文件存储（业务 + Milvus 共用） | `minioadmin` / `minioadmin` |
| `etcd` | `quay.io/coreos/etcd:v3.5.18` | 内部 | Milvus 元数据存储 | — |
| `standalone` | `milvusdb/milvus:v2.6.6` | `19530` / `9091` | Milvus 向量库（示例用） | — |
| `attu` | `zilliz/attu:v2.6.3` | `8000` | Milvus 可视化管理界面 | — |

启动命令：

```bash
docker-compose up -d
```

服务启动后可访问：

- Neo4j Browser：`http://localhost:7474`（账号 `neo4j` / `12345678`）
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

#### 第一步：启动所有中间件

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
mysql -h 127.0.0.1 -u root -proot < sql/Mysql/create_database_mysql.sql
mysql -h 127.0.0.1 -u root -proot < sql/Mysql/create_table_mysql.sql
```

PostgreSQL 需要手动启用 pgvector 扩展（首次使用执行一次）：

```bash
docker exec -it lin-ragagent-postgres psql -U postgres -d super_agent_pgvector -c "CREATE EXTENSION IF NOT EXISTS vector;"
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
