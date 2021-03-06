package br.ufpe.cin.if688.visitor;

import br.ufpe.cin.if688.ast.AssignStm;
import br.ufpe.cin.if688.ast.CompoundStm;
import br.ufpe.cin.if688.ast.EseqExp;
import br.ufpe.cin.if688.ast.Exp;
import br.ufpe.cin.if688.ast.ExpList;
import br.ufpe.cin.if688.ast.IdExp;
import br.ufpe.cin.if688.ast.LastExpList;
import br.ufpe.cin.if688.ast.NumExp;
import br.ufpe.cin.if688.ast.OpExp;
import br.ufpe.cin.if688.ast.PairExpList;
import br.ufpe.cin.if688.ast.PrintStm;
import br.ufpe.cin.if688.ast.Stm;
import br.ufpe.cin.if688.symboltable.IntAndTable;
import br.ufpe.cin.if688.symboltable.Table;

public class IntAndTableVisitor implements IVisitor<IntAndTable> {
	private Table t;

	public IntAndTableVisitor(Table t) {
		this.t = t;
	}

	@Override
	public IntAndTable visit(Stm s) {
		// TODO Auto-generated method stub
		return s.accept(this);
	}

	@Override
	public IntAndTable visit(AssignStm s) {
		// TODO Auto-generated method stub
		s.getExp().accept(this);
		return null;
	}

	@Override
	public IntAndTable visit(CompoundStm s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(PrintStm s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(Exp e) {
		// TODO Auto-generated method stub
		return e.accept(this);
	}

	@Override
	public IntAndTable visit(EseqExp e) {
		// TODO Auto-generated method stub
		Table newTable = e.getStm().accept(new Interpreter(this.t));
		int result = e.getExp().accept(new IntAndTableVisitor(newTable)).result;
		return new IntAndTable(result, this.t);
	}

	@Override
	public IntAndTable visit(IdExp e) {
		// TODO Auto-generated method stub
		Integer value = this.tableLookup(this.t, e.getId());
		return new IntAndTable(value, this.t);			
	}

	private Integer tableLookup(Table table, String id) {
		if (table == null) {
			return null;
		} else if (table.id.equals(id)) {
			return table.value;
		} else {
			return tableLookup(table.tail, id);
		}
	}
	
	@Override
	public IntAndTable visit(NumExp e) {
		// TODO Auto-generated method stub
		return new IntAndTable(e.getNum(), this.t);
	}

	@Override
	public IntAndTable visit(OpExp e) {
		// TODO Auto-generated method stub
		int leftRes = e.getLeft().accept(this).result;
		int rightRes = e.getRight().accept(this).result;
		
		switch (e.getOper()) {
			case OpExp.Div:
				return new IntAndTable(leftRes / rightRes, this.t);
			case OpExp.Minus:
				return new IntAndTable(leftRes - rightRes, this.t);
			case OpExp.Plus:
				return new IntAndTable(leftRes + rightRes, this.t);
			case OpExp.Times:
				return new IntAndTable(leftRes * rightRes, this.t);
		}
		return null;
	}

	@Override
	public IntAndTable visit(ExpList el) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(PairExpList el) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAndTable visit(LastExpList el) {
		// TODO Auto-generated method stub
		return null;
	}


}
