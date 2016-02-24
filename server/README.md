# cps371-examples
Code that might be useful, clarifying, or thought-inspiring for the class.

### Style notes:
 * 4-space indent; no tabs or trailing whitespace
   - Emacs users, see detailed notes below.

### Resources
 * [How to Write a Git Commit Message](http://chris.beams.io/posts/git-commit/)
 * [Douglas Crockford's JavaScript Conventions](http://javascript.crockford.com/code.html)

### Emacs helps:
Put the following in your .emacs file to follow whitespace guidelines automatically.
```
;; do all indenting with spaces (not tabs) so it works well with
;; other people's editors and displays well on github
(setq-default indent-tabs-mode nil)

;; delete trailing whitespace when saving buffer, to avoid whitespace
;; differences when coding collaboratively
(add-hook 'before-save-hook 'delete-trailing-whitespace)

;; indent braces naturally, and indent by 4 spaces per level
(setq c-default-style "linux"
      c-basic-offset 4)
```
