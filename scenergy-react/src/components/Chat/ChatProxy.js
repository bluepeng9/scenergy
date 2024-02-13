const { createProxyMiddleware } = require("http-proxy-middleware");

// 웹소켓 서버통신은 ws 프로토콜 사용. 소켓 프록시 따로 설정 필요함
module.exports = (app) => {
  app.use(
    "/ws",
      createProxyMiddleware({target: process.env.REACT_APP_API_URL + ", ws:true"})
  );
};
