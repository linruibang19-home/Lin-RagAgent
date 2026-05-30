# kuritian.online 公网上线操作手册

本文档用于将 Lin-RagAgent 从本地 Docker 环境发布为可公开访问的演示环境。

## 1. 推荐架构

| 组件 | 推荐平台 | 域名 | 说明 |
| --- | --- | --- | --- |
| Vue 前端 | Vercel | `kuritian.online`、`www.kuritian.online` | 静态站点，优先使用免费档 |
| Spring Boot 后端 | Render Web Service | `api.kuritian.online` | 使用仓库根目录 `Dockerfile` 构建 |
| MySQL | 托管 MySQL | 仅后端访问 | 保存业务数据、会话和任务状态 |
| PostgreSQL + pgvector | Render Postgres 或其他托管 PostgreSQL | 仅后端访问 | 保存向量数据 |
| Redis | Render Key Value 或其他托管 Redis | 仅后端访问 | 缓存、租约和分布式锁 |
| Kafka | 托管 Kafka | 仅后端访问 | 文档解析和索引构建异步任务 |
| Elasticsearch | 托管 Elasticsearch 或 Render Private Service | 仅后端访问 | 关键词检索 |
| 对象存储 | MinIO 或兼容 S3 的对象存储 | 仅后端访问 | 保存上传文件和解析文本 |
| Neo4j | 暂时关闭 | 无 | 首版设置 `NEO4J_ENABLED=false`，结构图逻辑回退到 MySQL |

前端和后端可以先使用平台提供的临时域名测试。确认业务链路可用后，再绑定 `kuritian.online`。

## 2. 当前仓库已提供的部署文件

| 文件 | 用途 |
| --- | --- |
| `Dockerfile` | 在 Render 构建 Spring Boot 后端镜像 |
| `.dockerignore` | 减少后端镜像构建上下文 |
| `render.yaml` | 创建 Render 后端 Web Service，并声明生产环境变量 |
| `vue/vercel.json` | 配置 Vue SPA 回退，并将前端 API 请求代理到 `https://api.kuritian.online` |
| `application-prod.yaml` | 从环境变量读取生产配置，并监听 Render 注入的 `PORT` |

## 3. 第一阶段：注册平台，不急于付费

1. 使用 GitHub 登录 [Vercel](https://vercel.com/signup)。
2. 使用 GitHub 登录 [Render](https://dashboard.render.com/register)。
3. 在两个平台中授权访问 GitHub 仓库 `linruibang19-home/Lin-RagAgent`。
4. 暂时不要购买套餐。先确认前端构建、后端容器构建和临时域名访问正常。

Vercel 免费档适合托管当前 Vue 前端。Render 免费 Web Service 会在闲置后休眠，适合验证部署流程，不适合作为稳定线上服务。

## 4. 部署 Render 后端

1. 登录 Render，选择 **New +** → **Blueprint**。
2. 连接 GitHub 仓库 `linruibang19-home/Lin-RagAgent`。
3. Render 会读取仓库根目录的 `render.yaml`，创建 `lin-ragagent-api`。
4. 在 Render 控制台补充所有标记为需要手工填写的环境变量。
5. 首次部署完成后，记录 Render 临时地址，例如 `https://lin-ragagent-api.onrender.com`。
6. 使用 `https://<Render 临时地址>/actuator/health` 检查后端健康状态。

### Render 环境变量

以下变量必须在 Render 控制台中填写。不要写入 GitHub。

| 变量 | 用途 |
| --- | --- |
| `MYSQL_HOST`、`MYSQL_USERNAME`、`MYSQL_PASSWORD` | 托管 MySQL 连接信息 |
| `REDIS_HOST`、`REDIS_PASSWORD` | 托管 Redis 连接信息 |
| `KAFKA_BOOTSTRAP_SERVERS` | 托管 Kafka Broker 地址 |
| `MINIO_ENDPOINT`、`MINIO_ACCESS_KEY`、`MINIO_SECRET_KEY` | 对象存储连接信息 |
| `PGVECTOR_HOST`、`PGVECTOR_USERNAME`、`PGVECTOR_PASSWORD` | PostgreSQL + pgvector 连接信息 |
| `ELASTICSEARCH_URI`、`ELASTICSEARCH_USERNAME`、`ELASTICSEARCH_PASSWORD` | Elasticsearch 连接信息 |
| `ALI_BAI_LIAN_API_KEY` | 阿里云百炼 API Key |
| `TAVILY_API_KEY` | Tavily 联网搜索 API Key |
| `SUPER_AGENT_ADMIN_USERNAME`、`SUPER_AGENT_ADMIN_PASSWORD` | 管理后台账号密码 |

首次上线前必须重新生成已经在聊天或截图中暴露过的 API Key 和密码。

### 数据初始化

1. MySQL 执行 `sql/Mysql/create_database_mysql.sql` 和 `sql/Mysql/create_table_mysql.sql`。
2. PostgreSQL 启用扩展：`CREATE EXTENSION IF NOT EXISTS vector;`
3. PostgreSQL 执行 `sql/PostgresSql/create_table_postgres_sql.sql`。
4. Kafka 创建以下 Topic：
   - `Lin-RagAgent-document-parse-route`
   - `Lin-RagAgent-document-index-build`

## 5. 部署 Vercel 前端

1. 登录 Vercel，选择 **Add New...** → **Project**。
2. 导入 GitHub 仓库 `linruibang19-home/Lin-RagAgent`。
3. 将 **Root Directory** 设置为 `vue`。
4. Framework Preset 选择 **Vite**。
5. Build Command 使用 `npm run build`。
6. Output Directory 使用 `dist`。
7. 部署后使用 Vercel 临时域名打开 `/chat`，检查页面是否显示。

`vue/vercel.json` 已将 `/api`、`/manage` 和 `/admin/auth` 转发到 `https://api.kuritian.online`。在后端域名绑定完成前，页面可以打开，但业务请求不会成功。

## 6. 绑定 kuritian.online

### Render

1. 打开 Render 的 `lin-ragagent-api` 服务。
2. 进入 **Settings** → **Custom Domains**。
3. 添加 `api.kuritian.online`。
4. 根据 Render 页面提示，在阿里云 DNS 中增加对应记录。

### Vercel

1. 打开 Vercel 前端项目。
2. 进入 **Settings** → **Domains**。
3. 添加 `kuritian.online` 和 `www.kuritian.online`。
4. 根据 Vercel 页面提示，在阿里云 DNS 中增加对应记录。

### 阿里云 DNS

在阿里云域名控制台中点击 `kuritian.online` 的 **解析**，按照 Vercel 和 Render 控制台给出的目标值创建记录。常见形式如下，最终以平台页面为准。

| 主机记录 | 记录类型 | 指向 |
| --- | --- | --- |
| `@` | `A` 或 `CNAME` | Vercel 提供的目标值 |
| `www` | `CNAME` | Vercel 提供的目标值 |
| `api` | `CNAME` | Render 提供的目标值 |

完成 DNS 验证后，Vercel 和 Render 会自动签发 HTTPS 证书。

## 7. 上线冒烟测试

1. 打开 `https://api.kuritian.online/actuator/health`，确认状态为 `UP`。
2. 打开 `https://kuritian.online/chat`，完成一次普通问答。
3. 完成一次 Tavily 联网天气查询。
4. 登录管理后台并上传一份 Markdown 文档。
5. 检查文档解析、策略确认、索引构建、Chunk 验证和任务详情。
6. 回到聊天页，使用已上传文档完成一次 RAG 问答。

## 8. 费用判断

可以先免费注册并完成前端部署和后端镜像构建验证。要让当前完整业务链路稳定运行，仍然需要长期可用的数据库、中间件和对象存储。

不要一次性购买全部资源。建议先确认以下事项：

1. 是否只需要临时作品演示，允许 Render 后端休眠。
2. 是否要保留 Kafka、Elasticsearch 和对象存储的完整文档工作流。
3. 是否需要 Neo4j 图谱展示。首版可以关闭。
4. 预计访问人数和每月预算。

确认后再选择托管服务套餐，避免为暂时不使用的资源付费。
