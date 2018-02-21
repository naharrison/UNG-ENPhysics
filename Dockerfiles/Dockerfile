FROM fedora:latest

WORKDIR /mydocker

RUN yum -y install which tcsh vim-enhanced

COPY fedora-repos/*.repo /etc/yum.repos.d/

ENTRYPOINT ["/bin/tcsh"]
