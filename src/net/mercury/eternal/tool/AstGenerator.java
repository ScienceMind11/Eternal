package net.mercury.eternal.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AstGenerator {

    public static void main(String[] args) throws IOException {

        String outputDir = args[0];

        defineAst(
                outputDir,
                "Expr",
                List.of(
                        "Binary   : Expr left, Token operator, Expr right",
                        "Grouping : Expr expression",
                        "Literal  : Object value",
                        "Unary    : Token operator, Expr right"
                )
        );

    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package net.mercury.eternal.syntax;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println("import net.mercury.eternal.token.Token;");
        writer.println();
        writer.println("public abstract class " + baseName + " {");
        writer.println();

        for(String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
        }

        writer.println();
        defineVisitor(writer, baseName, types);

        writer.println();
        writer.println("    abstract <R> R accept(Visitor<R> visitor);");

        writer.println();
        writer.println("}");
        writer.close();

    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {

        writer.println("    public static class " + className + " extends " + baseName + " {");
        writer.println();

        String[] fields = fieldList.split(", ");
        for(String field : fields) {
            writer.println("        final " + field + ";");
        }

        writer.println();
        writer.println("        public " + className + "(" + fieldList + ") {");

        for(String field : fields) {
            String name = field.split(" ")[1];
            writer.println("            this." + name + " = " + name + ";");
        }

        writer.println("        }");

        writer.println();
        writer.println("        @Override");
        writer.println("        <R> R accept(Visitor<R> visitor) {");
        writer.println("            return visitor.visit" + className + baseName + "(this);");
        writer.println("        }");

        writer.println();
        writer.println("    }");

    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {

        writer.println("    interface Visitor<R> {");

        for(String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println("        R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
        }

        writer.println("    }");

    }

}
