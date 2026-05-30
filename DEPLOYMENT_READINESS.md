# Lin-RagAgent 部署前修复记录

本记录用于跟踪上线前已经处理的工程问题，以及后续部署仍需要确认的事项。

## 本次已处理

- 移除 Java 源文件开头的 UTF-8 BOM，解决 Maven 编译时报 `非法字符: '\ufeff'` 的问题。
- 修正 `spring.factories` 中旧包名 `org.javaup.*`，统一指向当前源码包 `org.Lin.*`。
- 统一 Kafka 文档处理 topic 自动创建逻辑，使自动创建的 topic 与生产者、消费者实际使用的 `prefix-topic` 名称一致。
- 修正 `docker-compose.yml` 中 Attu 连接 Milvus 的服务名，避免指向不存在的 `milvus-standalone`。
- 增加 `application-prod.yaml`，将生产环境数据库、中间件、管理员账号、JWT secret、API key 等配置改为环境变量注入，并收敛 Actuator 暴露范围。
- 修正各模块 `AutoConfiguration.imports` 中遗留的 `org.javaup.*` 自动配置类名，避免 Spring Boot 启动时报 `Unable to read meta-data`。
- 修正主业务模块 `log4j2.xml` 的日志过滤包名前缀，使其与当前 `org.Lin` 包名一致。
- 将 Elasticsearch 默认索引名与 MinIO 默认 bucket 名改为小写，满足两类中间件的命名约束。

## 当前启动验证状态

- 后端主服务可以完成 Maven 编译与 Spring Boot 基础启动流程，已进入 Tomcat 和 Spring 容器初始化阶段。
- 当前本机未能完整启动成功，阻塞点为 MySQL 凭据不匹配：`Access denied for user 'root'@'localhost'`，导致 `MysqlSaver` 自动建表失败。
- 当前 Docker Desktop daemon 未运行，无法通过 `docker compose up -d` 直接拉起本地全量中间件栈。
- 当前 shell 未配置真实 `ALI_BAI_LIAN_API_KEY` 与 `TAVILY_API_KEY`。启动验证使用了占位值，只能验证容器装配，不能验证真实模型与联网搜索能力。
- 前端构建已通过 `npm run build` 验证，后续联调仍需要后端和中间件完整启动。

## 部署前仍需确认

- 生产环境必须显式设置 `SPRING_PROFILES_ACTIVE=prod`。
- MySQL、PostgreSQL、Redis、Kafka、Elasticsearch、Neo4j、MinIO 不建议直接使用本地 `docker-compose.yml` 暴露到公网。
- 本地启动前需要确认 MySQL 账号密码与 `application.yaml` 一致，默认是 `root` / `root`，并确保 `super_agent_business_chat` 可创建或已初始化。
- 生产 Kafka 如果关闭自动建 topic，需要手动创建：
  - `Lin-RagAgent-document-parse-route`
  - `Lin-RagAgent-document-index-build`
- 如果修改 `prefix.distinction.name` 或 `app.manage.kafka.*`，最终 topic 名称以 `DocumentKafkaTopicNames` 解析结果为准。
- PostgreSQL 需要先安装并启用 pgvector 扩展，再执行 `sql/PostgresSql/create_table_postgres_sql.sql`。
- 当前 pgvector 表使用未固定维度的 `VECTOR` 字段，尚未创建 HNSW/IVF 向量索引。数据量增长后需要根据 embedding 模型维度补充向量索引。
- 后台默认登录密码不能用于生产，必须设置 `SUPER_AGENT_ADMIN_PASSWORD`。
- `SUPER_AGENT_ADMIN_TOKEN_SECRET` 必须使用足够长的随机值，不能复用默认开发值。

## 推荐上线顺序

1. 准备 MySQL 与 PostgreSQL 数据库，并执行 `sql/` 下初始化脚本。
2. 准备 Redis、Kafka、Elasticsearch、Neo4j、MinIO。
3. 设置 `application-prod.yaml` 所需环境变量。
4. 构建后端 JAR，并使用 `SPRING_PROFILES_ACTIVE=prod` 启动。
5. 构建前端 `vue/dist`，由 Nginx 或同类静态服务托管。
6. 通过 `/actuator/health`、后台登录、文档上传、索引构建、RAG 问答做上线冒烟验证。
