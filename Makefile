.PHONY: dev backend frontend help

help:
	@echo "绿色债券 RAG 智能洗绿核验平台"
	@echo ""
	@echo "用法:"
	@echo "  make dev       同时启动前后端（推荐）"
	@echo "  make backend   只启动后端"
	@echo "  make frontend  只启动前端"
	@echo ""
	@echo "前提: 已安装 Maven 和 Node.js"
	@echo "详见 README.md"

dev:
	@./start-dev.sh

backend:
	@cd backend && mvn spring-boot:run

frontend:
	@cd frontend && npm run dev
