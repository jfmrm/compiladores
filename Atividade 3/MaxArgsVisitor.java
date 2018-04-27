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

public class MaxArgsVisitor implements IVisitor<Integer> {
	int maxArgs = 0;
	
	@Override
	public Integer visit(Stm s) {
		return s.accept(this);
	}

	@Override
	public Integer visit(AssignStm s) {
		return s.getExp().accept(this);
	}

	@Override
	public Integer visit(CompoundStm s) {
		int esq = s.getStm1().accept(this);
		int dir = s.getStm2().accept(this);
		return Math.max(esq, dir);
	}

	@Override
	public Integer visit(PrintStm s) {
		int args = s.getExps().accept(this);
		if (args > maxArgs) {
			maxArgs = args;
		}
		return maxArgs;
	}

	@Override
	public Integer visit(Exp e) {
		// TODO Auto-generated method stub
		return e.accept(this);
	}

	@Override
	public Integer visit(EseqExp e) {
		// TODO Auto-generated method stub
		int letf = e.getExp().accept(this);
		int right = e.getStm().accept(this);
		return Math.max(letf, right);
	}

	@Override
	public Integer visit(IdExp e) {
		return 0;
	}

	@Override
	public Integer visit(NumExp e) {
		return 0;
	}

	@Override
	public Integer visit(OpExp e) {
		// TODO Auto-generated method stub
		int left = e.getLeft().accept(this);
		int right = e.getRight().accept(this);
		return Math.max(left, right);
	}

	@Override
	public Integer visit(ExpList el) {
		// TODO Auto-generated method stub
		int args = el.accept(this);
		return args;
	}

	@Override
	public Integer visit(PairExpList el) {
		// TODO Auto-generated method stub
		el.getHead().accept(this);
		return (1 + el.getTail().accept(this));
	}

	@Override
	public Integer visit(LastExpList el) {
		// TODO Auto-generated method stub
		el.getHead().accept(this);
		return 1;
	}

}
