package file;
import java.util.*;

class SymbolEntry {
    String name;
    String type;
    String scope;
    String value;
    String memoryLocation;

    public SymbolEntry(String name, String type, String scope, String value, String memoryLocation) {
        this.name = name;
        this.type = type;
        this.scope = scope;
        this.value = value;
        this.memoryLocation = memoryLocation;
    }

    @Override
    public String toString() {
        return "Symbol(Name: " + name + ", Type: " + type + ", Scope: " + scope + ", Value: " + value + ", Memory: " + memoryLocation + ")";
    }
}


class SymbolTable {
    private Map<String, SymbolEntry> symbols;
    private int memoryCounter = 1000;

    public SymbolTable() {
        this.symbols = new HashMap<>();
    }

    public boolean insert(String name, String type, String scope, String value) {
        if (symbols.containsKey(name)) {
            return false; 
        }
        String memoryLocation = "M" + memoryCounter;
        memoryCounter += 4;
        symbols.put(name, new SymbolEntry(name, type, scope, value, memoryLocation));
        return true;
    }

    public void update(String name, String value) {
        if (symbols.containsKey(name)) {
            symbols.get(name).value = value;
        }
    }

    public void updateType(String name, String newType) {
        if (symbols.containsKey(name)) {
            symbols.get(name).type = newType;
        }
    }

    public boolean contains(String name) {
        return symbols.containsKey(name);
    }

    public SymbolEntry lookup(String name) {
        return symbols.get(name);
    }

    public void printTable() {
        System.out.println("\n--- Symbol Table ---");
        for (SymbolEntry entry : symbols.values()) {
            System.out.println(entry);
        }
    }
}

