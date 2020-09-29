package org.workholick.doc.model;

public class Docs {
    private String docTitle;
    private String docText;

    public  Docs(){

    }

    public Docs(String fileName){
        this.docTitle=fileName;
    }

    public String getDocText() {
        return docText;
    }

    public void setDocText(String docText) {
        this.docText = docText;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocTitle() {
        return docTitle;
    }
}
