import java.lang.reflect.Array;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GraphTraversal {

	private String start;
	private String end;
	// private Map<String, Node>nodes;
	private ArrayList<ArrayList<Node>> forwardPath;
	private ArrayList<ArrayList<Node>> loops;
	private ArrayList<Double> gainForwardPath;
	private ArrayList<Double> gainLoopsarray;
	private ArrayList<Integer>[] nuntouched;
	private double[] eahDelta;
	private ArrayList<Double> untouchedgains;
	private ArrayList<Node> path;
	private ArrayList<Double> edges;
	private double gainforward;
	private double gainloops;
	private double valueUntouched;
	private double deltaa;
	private double overall;
	private ArrayList<int[]> combinations;
	public GraphTraversal(String start, String end) {
		path = new ArrayList<>();
		forwardPath = new ArrayList<ArrayList<Node>>();
		loops = new ArrayList<ArrayList<Node>>();
		gainForwardPath = new ArrayList<Double>();
		gainLoopsarray = new ArrayList<Double>();
		edges = new ArrayList<Double>();
		this.start = start;
		this.end = end;
		// this.nuntouched=new ArrayList<ArrayList<Integer>>();
		gainforward = 1.0;
		gainloops = 1.0;
		this.untouchedgains = new ArrayList<Double>();
		this.valueUntouched = 0.0;
		this.deltaa = 0.0;
		this.overall = 0.0;
		this.combinations = new ArrayList<>();
	}

	public void dfs(Node started) {
		path.add(started);
		if (started.getName().equalsIgnoreCase(end)) {
			ArrayList<Node> c = (ArrayList<Node>) path.clone();
			forwardPath.add(c);
			gainForwardPath.add(gainforward);
		}
		// started.setVisited(true);
		for (int i = 0; i < started.getConnections().size(); i++) {

			if (started.getConnections().get(i).isVisited()) {
				path.add(started.getConnections().get(i));
				edges.add(started.getEdges().get(i));
				gainloops = gainloops * started.getEdges().get(i);
				gainforward = gainforward * started.getEdges().get(i);
				check(started.getConnections().get(i));
			} else {
				started.getConnections().get(i).setVisited(true);
				gainloops = gainloops * started.getEdges().get(i);
				gainforward = gainforward * started.getEdges().get(i);
				edges.add(started.getEdges().get(i));
				dfs(started.getConnections().get(i));
				started.getConnections().get(i).setVisited(false);
			}
			gainloops = gainloops / started.getEdges().get(i);
			gainforward = gainforward / started.getEdges().get(i);
			path.remove(path.size() - 1);
			edges.remove(edges.size() - 1);
			//
		}
		started.setVisited(false);
	}

	public void check(Node started) {
		ArrayList<Node> z = new ArrayList<Node>();
		boolean found = false;
		double r = 1.0;
		for (int i = 0; i < path.size(); i++) {
			if (path.get(i).getName().equalsIgnoreCase(started.getName())) {
				found = true;
			}
			if (found) {
				z.add(path.get(i));
			} else {
				r = r * edges.get(i);
			}
		}
		double e = gainloops / r;
		gainLoopsarray.add(e);
		loops.add(z);
	}

	public void removeDublicated() {
		for (int i = 0; i < loops.size(); i++) {
			for (int j = i + 1; j < loops.size(); j++) {
				if (loops.get(i).size() == loops.get(j).size()) {
					if (loops.get(j).contains(loops.get(i).get(0))) {
						int y = -1;
						for (int j2 = 0; j2 < loops.get(j).size(); j2++) {
							if (loops.get(j).get(j2)
									.equals(loops.get(i).get(0))) {
								y = j2;
								break;
							}
						}
						boolean found = false;
						int index = -1;
						for (int k = 0; k < loops.get(i).size() - 1; k++) {
							index = (k + y) % (loops.get(j).size() - 1);
							if (loops.get(i).get(k)
									.equals(loops.get(j).get(index))) {
							} else {
								found = true;
								break;
							}
						}
						if (!found) {
							loops.remove(j);
							gainLoopsarray.remove(j);
							j=i;
						}
					}
				}
			}
		}
	}

	public ArrayList<Double> getGainForwardPath() {
		return gainForwardPath;
	}

	public void nunTouched() {
		this.nuntouched = new ArrayList[loops.size()];
		for (int i = 0; i < nuntouched.length; i++) {
			nuntouched[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < loops.size(); i++) {
			for (int j = i + 1; j < loops.size(); j++) {
				boolean found = false;
				for (int j2 = 0; j2 < loops.get(i).size(); j2++) {
					if (loops.get(j).contains(loops.get(i).get(j2))) {
						found = true;
						break;
					}
				}
				if (!found) {
					nuntouched[i].add(j);
					nuntouched[j].add(i);
				}
			}
		}
	}

	public void setGainForwardPath(ArrayList<Double> gainForwardPath) {
		this.gainForwardPath = gainForwardPath;
	}

	public ArrayList<Double> getGainLoopsarray() {
		return gainLoopsarray;
	}

	public void setGainLoopsarray(ArrayList<Double> gainLoopsarray) {
		this.gainLoopsarray = gainLoopsarray;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public ArrayList<ArrayList<Node>> getForwardPath() {
		return (forwardPath);
	}

	public ArrayList<ArrayList<Node>> getLoops() {
		return (loops);
	}

	public ArrayList<Integer>[] getNuntouched() {
		return (nuntouched);
	}

	public void setNuntouched(ArrayList<Integer>[] nuntouched) {
		this.nuntouched = nuntouched;
	}

	public void combine(int[] arr, int k, int startId, int[] branch, int numElem) {
		if (numElem == k) {
			if (checkuntouched(branch)) {
				valueUntouched += calculate(branch);
				combinations.add(branch.clone());
			}
			return;
		}

		for (int i = startId; i < arr.length; ++i) {
			branch[numElem++] = arr[i];
			combine(arr, k, ++startId, branch, numElem);
			--numElem;
		}
	}

	public void fillUnTounched() {
		int[] size = new int[loops.size()];
		for (int i = 0; i < size.length; i++) {
			size[i] = i;
		}
		boolean found = true;
		int i = 2;
		while (found) {
			int[] branch = new int[i];
			valueUntouched = 0.0;
			combine(size, i, 0, branch, 0);
			untouchedgains.add(valueUntouched);
			if (valueUntouched == 0) {
				found = false;
			} else {
				i++;
			}
		}
	}

	public boolean checkuntouched(int[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (!(nuntouched[array[i]].contains(array[j]))) {
					return false;
				}
			}
		}
		return true;
	}

	public double calculate(int[] array) {
		double t = 1.0;
		for (int i = 0; i < array.length; i++) {
			t = t * gainLoopsarray.get(array[i]);
		}
		return t;
	}

	public double delta() {
		double t = 1.0;
		double y = 0.0;
		for (int i = 0; i < gainLoopsarray.size(); i++) {
			y += gainLoopsarray.get(i);
		}
		t = t - y;
		double i = 1.0;
		for (int j = 0; j < untouchedgains.size(); j++) {
			double r = untouchedgains.get(j) * i;
			t = t + r;
			i = i * -1;
		}
		return t;
	}

	public void deltaForPath(int index, ArrayList<Node> forwardPath) {
		ArrayList<Integer> loopsForPath = new ArrayList<Integer>();
		ArrayList<Integer> gains = new ArrayList<Integer>();
		for (int i = 0; i < loops.size(); i++) {
			boolean found = false;
			for (int j = 0; j < loops.get(i).size(); j++) {
				if (forwardPath.contains(loops.get(i).get(j))) {
					found = true;
					break;
				}
			}
			if (!found) {
				loopsForPath.add(i);
			}
		}
		int value = 0;
		int[] size = new int[loopsForPath.size()];
		for (int i = 0; i < size.length; i++) {
			size[i] = i;
		}
		boolean found = true;
		int i = 2;
		while (found) {
			int[] branch = new int[i];
			value = 0;
			combineForPath(value, size, i, 0, branch, 0);
			gains.add(value);
			if (value == 0) {
				found = false;
			} else {
				i++;
			}
		}
		int t = 1;
		int y = 0;
		for (int j = 0; j < loopsForPath.size(); j++) {
			y += gainLoopsarray.get(loopsForPath.get(j));
		}
		t = t - y;
		int k = 1;
		for (int j = 0; j < gains.size(); j++) {
			int r = gains.get(j) * k;
			t = t + r;
			k = k * -1;
		}
		eahDelta[index] = t;
	}

	public void caller(Node started) {
		started.setVisited(true);
		dfs(started);
		removeDublicated();
		nunTouched();
		fillUnTounched();
		allDelta();
		overall = overAllTrans();
	}

	public void combineForPath(int value, int[] arr, int k, int startId,
			int[] branch, int numElem) {
		if (numElem == k) {
			if (checkuntouched(branch)) {
				value += calculate(branch);
			}
			return;
		}

		for (int i = startId; i < arr.length; ++i) {
			branch[numElem++] = arr[i];
			combineForPath(value, arr, k, ++startId, branch, numElem);
			--numElem;
		}
	}

	public void allDelta() {
		eahDelta = new double[forwardPath.size()];
		for (int i = 0; i < forwardPath.size(); i++) {
			deltaForPath(i, forwardPath.get(i));
		}
	}

	public ArrayList<Double> getUntouchedgains() {
		return untouchedgains;
	}

	public void setUntouchedgains(ArrayList<Double> untouchedgains) {
		this.untouchedgains = untouchedgains;
	}

	public double[] getEahDelta() {
		return eahDelta;
	}

	public void setEahDelta(double[] eahDelta) {
		this.eahDelta = eahDelta;
	}

	public double overAllTrans() {
		double y = 0.0;
		deltaa = delta();
		for (int i = 0; i < forwardPath.size(); i++) {
			double r = eahDelta[i] * gainForwardPath.get(i);
			r = r / deltaa;
			y = y + r;
		}
		return y;
	}

	public double getDeltaa() {
		return deltaa;
	}

	public void setDeltaa(double deltaa) {
		this.deltaa = deltaa;
	}

	public double getOverall() {
		return overall;
	}

	public void setOverall(double overall) {
		this.overall = overall;
	}

	public void addLoop(ArrayList<Node> a, double gain) {
		loops.add(a);
		gainLoopsarray.add(gain);
	}

	public ArrayList<int[]> getCombinations() {
		return combinations;
	}

	public void setCombinations(ArrayList<int[]> combinations) {
		this.combinations = combinations;
	}

	//
	// public static void main(String[] args) {
	// GraphTraversal a = new GraphTraversal("1", "4");
	// Node one = new Node("1");
	// Node two = new Node("2");
	// Node three = new Node("3");
	// Node four = new Node("4");
	// Node five = new Node("5");
	// one.adderNode(two);
	// one.adderEdge(5);
	// one.adderNode(three);
	// one.adderEdge(2);
	// one.adderNode(four);
	// one.adderEdge(3);
	// two.adderNode(three);
	// two.adderEdge(4);
	// two.adderNode(four);
	// two.adderEdge(2);
	// three.adderNode(four);
	// three.adderEdge(3);
	// two.adderNode(one);
	// two.adderEdge(-4);
	// three.adderNode(one);
	// three.adderEdge(-1);
	// four.adderNode(two);
	// four.adderEdge(-5);
	// //
	// //
	// two.adderNode(one);
	// two.adderEdge(-3);
	// two.adderNode(two);
	// two.adderEdge(4);
	// one.adderNode(one);
	// one.adderEdge(7);
	// two.adderNode(three);
	// two.adderEdge(10);
	// three.adderNode(one);
	// three.adderEdge(-2);
	// one.adderNode(three);
	// one.adderEdge(150);
	// // one.adderNode(one);
	// // one.adderEdge(1);
	// // two.adderNode(two);
	// // two.adderEdge(1);
	// // three.adderNode(three);
	// // three.adderEdge(1);
	// // ArrayList<Node>b=new ArrayList<>();
	// // b.add(one);
	// // b.add(one);
	// // a.getLoops().add(b);
	// // a.getGainLoopsarray().add(2);
	// //
	// // ArrayList<Node>c=new ArrayList<>();
	// // c.add(two);
	// // c.add(two);
	// // a.getLoops().add(c);
	// // a.getGainLoopsarray().add(3);
	// //
	// // ArrayList<Node>d=new ArrayList<>();
	// // d.add(three);
	// // d.add(three);
	// // a.getLoops().add(d);
	// // a.getGainLoopsarray().add(4);
	// one.setVisited(true);
	// a.dfs(one);
	//
	// a.removeDublicated();
	// a.nunTouched();
	// a.fillUnTounched();
	// for (int i = 0; i < a.loops.size(); i++) {
	// for (int j = 0; j < a.loops.get(i).size(); j++) {
	// System.out.print(a.loops.get(i).get(j).getName()
	// + ", ");
	// }
	// System.out.println("gain : " + a.gainLoopsarray.get(i));
	// System.out.println();
	// }
	//
	// System.out.println("*********************************8");
	// for (int i = 0; i < a.getForwardPath().size(); i++) {
	// for (int j = 0; j < a.getForwardPath().get(i).size(); j++) {
	// System.out.print(a.getForwardPath().get(i).get(j).getName()
	// + ", ");
	// }
	// System.out.println("gain : " + a.gainForwardPath.get(i));
	// }
	// System.out.println("***************************************8");
	// for (int i = 0; i < a.nuntouched.length; i++) {
	// System.out.print("loop :"+i+"  ");
	// for (int j = 0; j < a.nuntouched[i].size(); j++) {
	// System.out.print(a.nuntouched[i].get(j)+" ");
	// }
	// System.out.println();
	// }
	// System.out.println("**********************************************8");
	// // for (int i = 0; i < a.getUntouchedgains().size(); i++) {
	// // System.out.println(a.getUntouchedgains().get(i));
	// // }
	// System.out.println(a.delta());
	// System.out.println("**********************************************8");
	// a.allDelta();
	// for (int i = 0; i <a.getEahDelta().length ; i++) {
	// System.out.println(i+"  :  "+a.getEahDelta()[i]);
	// }
	// System.out.println("****************************************************");
	// System.out.println(a.overAllTrans());
	//
	// }

}
