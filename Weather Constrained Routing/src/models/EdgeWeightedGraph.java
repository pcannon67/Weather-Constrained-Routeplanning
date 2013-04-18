package models;

public class EdgeWeightedGraph extends AbstractGraph {

	private final int V;
	private int E;
	private Bag<Edge>[] adj;

	public EdgeWeightedGraph(int V) {
		super(V);
		this.V = V;
		this.E = 0;
		adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
	}
	
	public EdgeWeightedGraph(int V, int E) {
        this(V);
        if (E < 0) throw new IllegalArgumentException("Number of edges in a Graph must be nonnegative");
        for (int i = 0; i < E; i++) {
            Node v = new Node((int) (Math.random() * V));
            Node w = new Node((int) (Math.random() * V));
            double weight = Math.round(100 * Math.random()) / 100.0;
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }
	
	public void addEdge(Edge e) {
        Node v = e.getNodeFrom();
        Node w = e.other(v);
        adj[Integer.valueOf(v.getId())].add(e);
        adj[Integer.valueOf(w.getId())].add(e);
        E++;
    }

	@Override
	public int V() {
		return V;
	}

	@Override
	public int E() {
		return E;
	}

	@Override
	public void addEdge(Node n, Node u) {
		
	}

	@Override
	public Iterable<Node> adj(Node n) {
		// TODO Auto-generated method stub
		return null;
	}

}
