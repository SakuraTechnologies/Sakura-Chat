sudo apt-get update
sudo apt-get install openssl
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout src/main/resources/ssl/server.key -out src/main/resources/ssl/server.crt
