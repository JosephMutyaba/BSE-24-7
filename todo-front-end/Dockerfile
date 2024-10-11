# Step 1: Use Node.js image to build the React app
FROM node:alpine as build
WORKDIR /app
COPY package*.json ./

# Clear npm cache and install dependencies
RUN npm cache clean --force
RUN npm install

COPY . .
RUN npm run build
