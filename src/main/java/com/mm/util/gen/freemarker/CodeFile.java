package com.mm.util.gen.freemarker;

public abstract class CodeFile {
    private String path;
    private String name;
    private String content;

    public CodeFile() {
    }

    public CodeFile(String path, String name, String content) {
        this.path = path;
        this.name = name;
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
