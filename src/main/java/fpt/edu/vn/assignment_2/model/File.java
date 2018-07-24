package fpt.edu.vn.assignment_2.model;

public class File {

    private String type;
    private String extension;
    private String encode;
    private String data;

    public File() {
    }

    public File(String type, String extension, String encode, String data) {
        this.type = type;
        this.extension = extension;
        this.encode = encode;
        this.data = data;
    }

    public void toExample() {
        if (type == null) type = "";
        if (extension == null) extension = "";
        if (encode == null) encode = "";
        if (data == null) data = "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
