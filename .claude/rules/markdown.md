---
paths:
  - "**/*.md"
  - ".markdownlint-cli2.yaml"
  - "lychee.toml"
  - ".lycheeignore"
---

# Markdown checks

```bash
npx markdownlint-cli2           # lint Markdown files (config: .markdownlint-cli2.yaml)
lychee .                        # check URLs in Markdown files (config: lychee.toml, ignore list: .lycheeignore)
```

GitHub Actions (`.github/workflows/check-markdown.yml`) runs both on pull requests that touch Markdown files or their
configuration. Both tools skip the third-party skill `.claude/skills/caveman-commit/` (managed through
`skills-lock.json`) and git-ignored files; first-party guidance under `.claude/` is checked. Every `.lycheeignore`
entry needs a dated comment explaining why the URL is excluded.
