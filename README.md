# 绿色债券 RAG 智能洗绿核验平台

> Green Bond RAG Greenwashing Verification Platform

一个本地可运行的绿色债券洗绿核验系统骨架，基于 **Spring Boot 3 + Spring AI + Vue 3**。
支持自由填写 **API KEY** 和 **模型名称**，默认接入 Kimi/Moonshot，也可切换到 DeepSeek、OpenAI 等其他 OpenAI 兼容平台。

---

## 目录结构

```
.
├── backend/              # Spring Boot 3 后端
│   ├── pom.xml
│   ├── src/main/java/com/gbrag/
│   │   ├── GbragApplication.java
│   │   ├── config/AIConfig.java
│   │   ├── controller/
│   │   │   ├── ChatController.java      # POST /api/chat
│   │   │   ├── VerifyController.java    # POST /api/verify
│   │   │   └── ConfigController.java    # GET  /api/config/model
│   │   └── dto/                     # 请求/响应 DTO
│   └── src/main/resources/
│       ├── application.yml              # 主配置（含 API KEY 和 model）
│       └── application-local.yml.template # 本地配置模板
├── frontend/             # Vue 3 前端（最小可运行版本）
│   ├── package.json
│   ├── vite.config.js
│   ├── index.html
│   └── src/
│       ├── main.js
│       ├── App.vue
│       └── api.js
└── docs/                 # 项目设计文档
    ├── 01-overview.md
    ├── 02-architecture.md
    ├── 03-tech-stack.md
    ├── 04-data-flow.md
    ├── 05-modules.md
    └── 06-roadmap.md
```

---

## 环境要求

- JDK 17+（推荐 JDK 21）
- Maven 3.9+
- Node.js 18+
- 一个 OpenAI 兼容的 API KEY（Kimi/Moonshot、DeepSeek、OpenAI 等）

---

## 快速开始

### 1. 配置 API KEY 和模型

打开 `backend/src/main/resources/application.yml`，修改以下字段：

```yaml
spring:
  ai:
    openai:
      api-key: sk-your-api-key-here
      base-url: https://api.moonshot.cn/v1
      chat:
        options:
          model: kimi-k3
```

常见平台配置对照：

| 平台 | base-url | model 示例 |
|------|----------|------------|
| Kimi/Moonshot 国内 | `https://api.moonshot.cn` | `kimi-k3` |
| DeepSeek | `https://api.deepseek.com` | `deepseek-chat` |
| OpenAI | `https://api.openai.com` | `gpt-4o` |

也可以复制 `application-local.yml.template` 为 `application-local.yml`，填写后运行：

```bash
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### 1. 配置 API KEY 和模型

打开 `backend/src/main/resources/application.yml`，修改以下字段：

```yaml
spring:
  ai:
    openai:
      api-key: sk-your-api-key-here
      base-url: https://api.moonshot.cn
      chat:
        options:
          model: kimi-k3
```

### 2. 一行代码启动前后端

```bash
make dev
```

或直接运行：

```bash
./start-dev.sh
```

这会同时启动：
- 后端 http://localhost:8080
- 前端 http://localhost:5173

按 `Ctrl+C` 即可同时停止两个服务。

### 3. 分开启动（可选）

```bash
# 后端
cd backend
mvn spring-boot:run

# 前端
cd frontend
npm install
npm run dev
```

---

## 接口说明

### GET /api/config/model

查看当前模型配置（API KEY 会脱敏显示）。

```json
{
  "model": "kimi-k3",
  "baseUrl": "https://api.moonshot.cn/v1",
  "maskedKey": "sk-3PM...d5h"
}
```

### POST /api/chat

与模型聊天。

```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"什么是绿色债券洗绿"}'
```

### POST /api/verify

洗绿风险核验（当前为简单模板演示，后续接入 RAG 知识库后能力会大幅提升）。

```bash
curl -X POST http://localhost:8080/api/verify \
  -H "Content-Type: application/json" \
  -d '{"content":"本债券募集资金全部用于新能源汽车项目，预计年减排 1000 万吨二氧化碳"}'
```

---

## 开发路线图

见 [docs/06-roadmap.md](docs/06-roadmap.md)

---

## 贡献者

- 刘昊杰（通达 89 班）

## License

MIT License
