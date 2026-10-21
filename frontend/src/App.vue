<template>
  <div class="container">
    <header>
      <h1>绿色债券 RAG 智能洗绿核验平台</h1>
      <p class="model-info">
        当前模型：{{ modelInfo.model || '加载中...' }} | 接口：{{ modelInfo.baseUrl || '-' }} | Key：{{ modelInfo.maskedKey || '-' }}
      </p>
    </header>

    <section class="card">
      <h2>聊天</h2>
      <textarea v-model="chatInput" rows="4" placeholder="输入你的问题..."></textarea>
      <button @click="sendChat" :disabled="chatting">发送</button>
      <div v-if="chatReply" class="result">
        <strong>回复：</strong>
        <pre>{{ chatReply }}</pre>
      </div>
    </section>

    <section class="card">
      <h2>洗绿核验</h2>
      <textarea v-model="verifyInput" rows="6" placeholder="粘贴绿色债券相关文本..."></textarea>
      <button @click="sendVerify" :disabled="verifying">开始核验</button>
      <div v-if="verifyResult" class="result">
        <strong>结论：</strong>{{ verifyResult.conclusion }}
        <br><br>
        <strong>依据：</strong>
        <pre>{{ verifyResult.evidence }}</pre>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getModelInfo, chat, verify } from './api.js'

const modelInfo = ref({})
const chatInput = ref('')
const chatReply = ref('')
const chatting = ref(false)
const verifyInput = ref('')
const verifyResult = ref(null)
const verifying = ref(false)

onMounted(async () => {
  try {
    const { data } = await getModelInfo()
    modelInfo.value = data
  } catch (e) {
    modelInfo.value = { model: '无法获取', baseUrl: '-', maskedKey: '-' }
  }
})

async function sendChat() {
  if (!chatInput.value.trim()) return
  chatting.value = true
  chatReply.value = ''
  try {
    const { data } = await chat(chatInput.value)
    chatReply.value = data.reply
  } catch (e) {
    chatReply.value = '请求失败：' + (e.response?.data?.message || e.message)
  } finally {
    chatting.value = false
  }
}

async function sendVerify() {
  if (!verifyInput.value.trim()) return
  verifying.value = true
  verifyResult.value = null
  try {
    const { data } = await verify(verifyInput.value)
    verifyResult.value = data
  } catch (e) {
    verifyResult.value = { conclusion: '失败', evidence: e.response?.data?.message || e.message }
  } finally {
    verifying.value = false
  }
}
</script>

<style>
body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  background: #f5f5f5;
  margin: 0;
  padding: 20px;
}
.container {
  max-width: 800px;
  margin: 0 auto;
}
header {
  text-align: center;
  margin-bottom: 24px;
}
.model-info {
  color: #666;
  font-size: 14px;
}
.card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
}
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-sizing: border-box;
  resize: vertical;
}
button {
  margin-top: 12px;
  padding: 10px 20px;
  background: #2a9d8f;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}
button:disabled {
  background: #94d2bd;
}
.result {
  margin-top: 16px;
  padding: 12px;
  background: #f8f9fa;
  border-radius: 8px;
}
pre {
  white-space: pre-wrap;
  word-break: break-word;
  margin: 8px 0 0;
}
</style>
