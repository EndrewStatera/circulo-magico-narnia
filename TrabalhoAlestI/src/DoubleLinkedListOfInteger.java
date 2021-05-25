
public class DoubleLinkedListOfInteger {

	private Node header;
	private Node trailer;
	private Node current;
	private Node ativo;
	private Node rei;
	private Node meio;
	private int posElementoAtivo;
	public long cont = 0;

	public DoubleLinkedListOfInteger() {
		header = new Node(null);
		trailer = new Node(null);
		header.next = trailer;
		trailer.prev = header;
		count = 0;
		posElementoAtivo = 0;
	}

	private int count;

	private class Node {
		public Integer element;
		public Node next;
		public Node prev;

		public Node(Integer e) {
			element = e;
			next = null;
			prev = null;
		}

		public String toString() {
			return "" + this.element;
		}
	}

	public void reset() {
		this.current = header.next;
	}

	public Integer next() {
		if (current == trailer) {
			return null;
		} else {
			Node aux = current;
			this.current = current.next;
			return aux.element;
		}

	}

	public void add(Integer element) {
		if (element == null)
			throw new IllegalArgumentException();
		Node n = new Node(element);
		n.next = trailer;
		n.prev = trailer.prev;
		Node aux = trailer.prev;
		aux.next = n;
		trailer.prev = n;
		ativo = n;
		if (rei == null) {
			rei = n;
			posElementoAtivo--;
		}
		posElementoAtivo = count;
		count++;
	}


	public void add(Integer element, int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= count) // indice invalido
			throw new IndexOutOfBoundsException();
		if (index == count - 1)
			add(element);
		else {
			Node n = new Node(element);
			Node aux = getCadeiraByIndex(index);
			n.prev = aux;
			n.next = aux.next;
			aux.next.prev = n;
			aux.next = n;
			count++;
			this.posElementoAtivo = index + 1;
			ativo = n;
		}

	}


	private Node getCadeiraByIndex(int index) {
		if ((index < 0) || (index >= count)) {
			throw new IndexOutOfBoundsException();
		}
		int metade = count/2;
		if(index < metade) {
			int diferencaAtivo = Math.abs(index - posElementoAtivo);
			if(diferencaAtivo < index) {
				Node aux = ativo;
				if(posElementoAtivo < index) {
					for(int i = posElementoAtivo; i < index; i++) {
						aux = aux.next;
						cont++;
					}
					return aux;
				}else {
					for(int i = posElementoAtivo; i > index; i--) {
						aux = aux.prev;
						cont++;
					}
					return aux;
				}
			}else {
				Node aux = header.next;
				for(int i = 0; i < index; i++) {
					aux = aux.next;
					cont++;
				}
				return aux;
			}
		}else {
			int diferenca = count - 1 - index;
			int diferencaAtivo = Math.abs(posElementoAtivo - index);
			if(diferencaAtivo < diferenca) {
				Node aux = ativo;
				if(posElementoAtivo > index) {
					for(int i = posElementoAtivo; i > index; i--) {
						aux = aux.prev;
						cont++;
					}
					return aux;
				}else {
					for(int i = posElementoAtivo; i < index; i++) {
						aux = aux.next;
						cont++;
					}
					return aux;
				}
			}else {
				Node aux = trailer.prev;
				for(int i = count-1; i > index; i--) {
					aux =aux.prev;
					cont++;
				}
				return aux;
			}
		}
	}


	public void clear() {
		header = new Node(null);
		trailer = new Node(null);
		header.next = trailer;
		trailer.prev = header;
		count = 0;
	}

	public int size() {
		return count;
	}

	public boolean isEmpty() {
		return (count == 0);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		Node aux = header.next;
		for (int i = 0; i < count; i++) {
			s.append(aux.element.toString());
			s.append("\n");
			aux = aux.next;
		}
		return s.toString();
	}


	private int getSomaVizinhos() {
		Node vizinhoAnt = ativo.prev;
		Node vizinhoPos = ativo.next;
		Integer vizinhoPrev = vizinhoAnt.element;
		Integer vizinhoNext = vizinhoPos.element;

		if (vizinhoAnt.equals(header)) {
			vizinhoPrev = trailer.prev.element;
			if (vizinhoPrev == null || trailer.prev.equals(ativo))
				vizinhoPrev = 0;
		}
		if (vizinhoPos.equals(trailer)) {
			vizinhoNext = header.next.element;
			if (vizinhoNext == null || header.next.equals(ativo))
				vizinhoNext = 0;
		}

		return vizinhoNext + vizinhoPrev;
	}

	public void addNoCirculoMagico(Integer element) {
		if (size() == 0) {
			add(element);
			return;
		}
		int passos = getSomaVizinhos();
		int attPosAtivo;
		if (passos > size()) {
			passos = passos % size();
			if (this.posElementoAtivo + passos >= size()) {
				attPosAtivo = passos - (size() - this.posElementoAtivo);// -1?
				add(element, attPosAtivo);// Deve atualizar o elemento ativo
			} else {
				attPosAtivo = this.posElementoAtivo + passos;// +1?
				add(element, attPosAtivo);
			}
		} else {
			if (this.posElementoAtivo + passos >= size()) {
				attPosAtivo = passos - (size() - this.posElementoAtivo);// -1?
				add(element, attPosAtivo);// Deve atualizar o elemento ativo
			} else {
				attPosAtivo = this.posElementoAtivo + passos;
				add(element, attPosAtivo);
			}
		}
	}

	public String ladoRei() {
		Node prev, next = null;
		if (rei.prev.equals(header)) {
			prev = trailer.prev;
		} else {
			prev = rei.prev;
		}

		if (rei.next.equals(trailer)) {
			next = header.next;
		} else {
			next = rei.next;
		}
		return "Situacao com " + (count - 1) + " habitantes: " + "\nLado esquerdo do rei: Cidadao " + prev.element
				+ "\nLado direito do rei: Cidadao" + next.element;
	}

	public String lista() {
		Node aux = header.next;
		String lista = "";
		while (aux != trailer) {
			if (aux.equals(ativo)) {
				lista += "[" + aux.element + "]";
			} else {
				lista += " " + aux;
			}
			aux = aux.next;
		}

		String espaco = "";
		for (int i = 0; i < 60 - lista.length(); i++) {
			espaco += " ";
		}
		return lista + espaco + "Posicao elemento ativo: " + this.posElementoAtivo;
	}
}
