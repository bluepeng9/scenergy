FROM node:21
RUN npm install -g serve
RUN mkdir ./build
ADD ./build ./build
ENTRYPOINT ["serve", "-s", "build"]