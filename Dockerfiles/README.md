Start the Docker daemon:
```tcsh
sudo systemctl start docker
```
Run Image command:
```tcsh
sudo docker run -it --rm --name tcanvas tcanvas:v1
```
Create Image command:
```tcsh
sudo docker build -t tcanvas:v1
```
