# Step 1: Use Node.js image to build the React app
FROM node:alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Step 2: Use Nginx to serve the built React app
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
