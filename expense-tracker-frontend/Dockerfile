# Use Node.js as the base image for building the Angular app
FROM node:18 AS build

WORKDIR /app

# Copy the package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy the rest of the app and build it
COPY . .
RUN npm run build --prod

# Use NGINX to serve the built app
FROM nginx:alpine

# Copy the build output from the build stage
COPY --from=build /app/dist/expense-tracker-frontend /usr/share/nginx/html

# Expose port 80 to serve the Angular app
EXPOSE 80

