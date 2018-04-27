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

public class Interpreter implements IVisitor<Table> {
	
	//a=8;b=80;a=7;
	// a->7 ==> b->80 ==> a->8 ==> NIL
	private Table t;
	
	public Interpreter(Table t) {
		this.t = t;
	}

	@Override
	public Table visit(Stm s) {
		// TODO Auto-generated method stub
		return s.accept(this);
	}

	@Override
	public Table visit(AssignStm s) {
		// TODO Auto-generated method stub
		String id = s.getId();
		int result = s.getExp().accept(new IntAndTableVisitor(t)).result;
		return new Table(id, result, t);
	}

	@Override
	public Table visit(CompoundStm s) {
		// TODO Auto-generated method stub
		t = s.getStm1().accept(this);
		t = s.getStm2().accept(this);
		return null;
	}

	@Override
	public Table visit(PrintStm s) {
		// TODO Auto-generated method stub
		return s.getExps().accept(this);
	}

	@Override
	public Table visit(Exp e) {
		// TODO Auto-generated method stub
		e.accept(new IntAndTableVisitor(t));
		return null;
	}

	@Override
	public Table visit(EseqExp e) {
		// TODO Auto-generated method stub
		e.getExp().accept(this);
		e.getStm().accept(this);
		return null;
	}

	@Override
	public Table visit(IdExp e) {
		// TODO Auto-generated method stub
		e.accept(new IntAndTableVisitor(t));
		return null;
	}

	@Override
	public Table visit(NumExp e) {
		// TODO Auto-generated method stub
		e.accept(new IntAndTableVisitor(t));
		return null;
	}

	@Override
	public Table visit(OpExp e) {
		// TODO Auto-generated method stub
		e.accept(new IntAndTableVisitor(t));
		return null;
	}

	@Override
	public Table visit(ExpList el) {
		// TODO Auto-generated method stub
		el.accept(this);
		return t;
	}

	@Override
	public Table visit(PairExpList el) {
		// TODO Auto-generated method stub
		System.out.printf("%.1f\n", (double) el.getHead().accept(new IntAndTableVisitor(t)).result);
		el.getTail().accept(this);
		return t;
	}

	@Override
	public Table visit(LastExpList el) {
		// TODO Auto-generated method stub
		System.out.printf("%.1f\n", (double) el.getHead().accept(new IntAndTableVisitor(t)).result);
		return t;
	}


}
