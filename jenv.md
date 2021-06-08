using multiple SDKs at the same time: https://www.jenv.be/

# installation

1. install via brew: `brew install jenv`
1. configure (install oh my zsh first):
    1. `echo 'export PATH="$HOME/.jenv/bin:$PATH"' >> ~/.zshrc`
    1. `echo 'eval "$(jenv init -)"' >> ~/.zshrc`
1. install some SDKs (8 and the latest) via brew ...
    1. update brew casks
        * `brew tap homebrew/cask-versions`
        * `brew update`
        * `brew tap caskroom/cask`
    1. list some: `brew search java`
    1. get details: `brew info java`
    1. `brew tap adoptopenjdk/openjdk`
    1. install JDK 8: `brew cask install adoptopenjdk8`

# usage

* list SDKs: `jenv versions`

``` 
  system
  1.8
  1.8.0.282
* 16 (set by /Users/cpickl/.jenv/version)
  16.0
  16.0.1
  openjdk64-1.8.0.282
  openjdk64-16.0.1
```

* add SDK: `jenv add /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home`
* change global: `jenv global 1.8`
* change per directory: `jenv local 16`
* per terminal session: `jenv shell 11`
