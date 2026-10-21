#!/bin/bash
# 一行代码同时启动后端 + 前端
# 使用: ./start-dev.sh
# 或: make dev

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
BACKEND_DIR="$SCRIPT_DIR/backend"
FRONTEND_DIR="$SCRIPT_DIR/frontend"

# 检查必要工具
command -v mvn >/dev/null 2>&1 || { echo "缺少 mvn，请先安装 Maven"; exit 1; }
command -v npm >/dev/null 2>&1 || { echo "缺少 npm，请先安装 Node.js"; exit 1; }

BACKEND_PID=""
FRONTEND_PID=""

shutdown() {
    echo ""
    echo "正在关闭前后端服务..."
    [ -n "$BACKEND_PID" ] && kill "$BACKEND_PID" 2>/dev/null || true
    [ -n "$FRONTEND_PID" ] && kill "$FRONTEND_PID" 2>/dev/null || true
    wait "$BACKEND_PID" 2>/dev/null || true
    wait "$FRONTEND_PID" 2>/dev/null || true
    echo "已停止"
    exit 0
}
trap shutdown INT TERM EXIT

echo "启动后端（Spring Boot）..."
cd "$BACKEND_DIR"
mvn spring-boot:run -q &
BACKEND_PID=$!

echo "启动前端（Vue 3 + Vite）..."
cd "$FRONTEND_DIR"
if [ ! -d "node_modules" ]; then
    echo "  检测到没有 node_modules，正在执行 npm install..."
    npm install
fi
npm run dev -- --host &
FRONTEND_PID=$!

echo ""
echo "=================================="
echo "后端: http://localhost:8080"
echo "前端: http://localhost:5173"
echo "API 文档: http://localhost:8080/api/config/model"
echo ""
echo "按 Ctrl+C 可同时停止两个服务"
echo "=================================="
echo ""

wait "$BACKEND_PID"
