as seen in: https://danielmarklund.com/how-to-pimp-your-terminal-macos

1. prerequisites
    1. install CLI tools for xcode: `xcode-select --install`
    1.
    install [homebrew](https://brew.sh/): `/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"`
1. install iTerm: https://iterm2.com/downloads.html (via brew cask seems not to work)
    1. open iTerm2 and select from menubar `Make iTerm2 Default Term` (also enable: `Secure Keyboard Entry`)
1. install oh my zsh
    1. `sh -c "$(curl -fsSL https://raw.github.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"`
    1. install auto
       suggestion: `git clone https://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions`

1. common aliases in `.zshrc`:

```
alias l=ls
alias ll="ls -la"
alias c=cd
alias g=git
alias gw=./gradlew
alias d=docker
alias dc=docker-compose
alias k=kubectl
```

1. common aliases for git:

```
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status
```