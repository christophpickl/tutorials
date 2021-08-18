# apache 4 macOS

## Control

* start: `sudo apachectl start`
* stop: `sudo apachectl stop`
* restart: `sudo apachectl restart`
* root documents reside in: `/Library/WebServer/Documents/index.html.en`

## Setting up

* create directory `~/Sites`
* Go to `/etc/apache2/users` and edit the `<USERNAME>.conf` file and modify this one:

```
<Directory "/Users/developer/Sites/">
        Options Indexes MultiViews
        Require all granted
</Directory>
```

to:

```
<Directory "/Users/developer/Sites/">
        AllowOverride All
        Options Indexes MultiViews FollowSymLinks
        Require all granted
</Directory>
```

* backup the configuration files:
	1. `cd /etc/apache2`
	1. `sudo cp httpd.conf httpd.conf.bak`
	1. `cd /etc/apache2/extra`
	1. `sudo cp httpd-userdir.conf httpd-userdir.conf.bak`
* edit `/etc/apache2/extra/httpd-userdir.conf`
	* uncomment (line 16): `#Include /private/etc/apache2/users/*.conf`
* edit `/etc/apache2/httpd.conf`
	* uncomment (line 113): `#LoadModule include_module libexec/apache2/mod_include.so`
	* uncomment (line 186,187): `#LoadModule rewrite_module libexec/apache2/mod_rewrite.so` and `#LoadModule php7_module libexec/apache2/libphp7.so`
	* uncomment (line 523): `#Include /private/etc/apache2/extra/httpd-userdir.conf`
	* uncomment (line 555): `#Include /private/etc/apache2/other/*.conf`
	* ensure these are also uncommented:

```
LoadModule authn_core_module libexec/apache2/mod_authn_core.so
LoadModule authz_host_module libexec/apache2/mod_authz_host.so
LoadModule userdir_module libexec/apache2/mod_userdir.so
LoadModule rewrite_module libexec/apache2/mod_rewrite.so
```

* after those changes, restart the server
* test the page: `http://localhost/~username` (username based on `whoami`)

## Auto startup

* Enable apache: `sudo launchctl load -w /System/Library/LaunchDaemons/org.apache.httpd.plist`
* Check if it's running: `ps -aef | grep httpd`
* Disable again: `sudo launchctl unload -w /System/Library/LaunchDaemons/org.apache.httpd.plist`
