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
sudo docker build -t tcanvas:v1 .
```

## Useful links/commands:

https://www.projectatomic.io/blog/2015/07/what-are-docker-none-none-images/

```tcsh
sudo docker system prune
sudo docker rmi -f \<Image ID\> # to remove an image
sudo docker rmi -f `sudo docker images -a -q` # to remove all images (tcsh)
docker rmi $(docker images -a -q) # bash
alias doc sudo docker
```

## Troubleshooting

If Docker is misbehaving, try killing all running Docker related processes and start from scratch:
```tcsh
ps -ef | grep docker
sudo kill jobID1 jobID2 ...
```
Restarting your machine could also help
