FROM nginx:stable-alpine as production-stage
COPY /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
