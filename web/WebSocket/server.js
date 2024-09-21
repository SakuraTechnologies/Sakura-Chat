const http = require('http');
const WebSocket = require('ws');

const server = http.createServer();
const wss = new WebSocket.Server({ server });

wss.on('connection', (ws) => {
  console.log('WebSocket连接已');

  ws.on('message', (message) => {
    console.log(`收到消息: ${message}`);

    // Send a response back to the client
    ws.send('消息已收到');
  });

  ws.on('close', () => {
    console.log('WebSocket连接已关闭');
  });
});

server.on('request', (req, res) => {
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end('Hello World\n');
});

server.listen(8080,()=>{
    console.log('服务器已启动,端口号为8080');
});
