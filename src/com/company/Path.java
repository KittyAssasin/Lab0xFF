package com.company;

public class Path {
    private final int[] vertexes;
    private final double cost;

    public Path(int[] vertexes, double cost) {
        this.vertexes = vertexes;
        this.cost = cost;
    }

    public double getCost() {return cost;}
    public int[] getVertexes() {return vertexes;}
    public int getVertexCount() {return vertexes.length;}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Path) {
            if (this.getCost() != ((Path) obj).getCost())
                return false;
            if (this.getVertexCount() != ((Path) obj).getVertexCount())
                return false;
            for (int i = 0; i < this.vertexes.length; i++)
                if (this.vertexes[i] != ((Path) obj).vertexes[i])
                    return false;
            return true;
        } else
            return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Path{");
        sb.append(cost + ",[0,");
        for (int i = 0; i < vertexes.length; i++) {
            sb.append(vertexes[i] + ",");
        }
        sb.append("0]}");
        return sb.toString();
    }
}
