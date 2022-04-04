package de.dhbw.caput.tinf20b1.thompson;

abstract class RegularExpression {
	
	private RegularExpression( ){
		super( );
	}
	
	abstract FiniteStateAutomaton generateFSA( );
	
	
	
	final static class EmptySet extends RegularExpression {
		public EmptySet( ){
			super( );
		}
		
		@Override
		public String toString( ){
			return "∅";
		}
		
		@Override
		FiniteStateAutomaton generateFSA( ){
			return new FiniteStateAutomaton( );
		}
	}
	
	final static class EmptyWord extends RegularExpression {
		public EmptyWord( ){
			super( );
		}
		
		@Override
		public String toString( ){
			return "ε";
		}
		
		@Override
		FiniteStateAutomaton generateFSA( ){
			FiniteStateAutomaton fsa = new FiniteStateAutomaton( );
			fsa.INITIAL_STATE.addEpsilonTransitionTo( fsa.ACCEPTING_STATE );
			return fsa;
		}
	}
	
	final static class Literal extends RegularExpression {
		private final char LITERAL;
		
		public Literal( char literal ){
			super( );
			LITERAL = literal;
		}
		
		@Override
		public String toString( ){
			return String.valueOf( LITERAL );
		}
		
		@Override
		FiniteStateAutomaton generateFSA( ){
			FiniteStateAutomaton fsa = new FiniteStateAutomaton( );
			fsa.INITIAL_STATE.addTransition( LITERAL, fsa.ACCEPTING_STATE );
			return fsa;
		}
	}
	
	final static class Union extends RegularExpression {
		private final RegularExpression FIRST;
		private final RegularExpression SECOND;
		
		public Union( RegularExpression first, RegularExpression second ){
			super( );
			FIRST = first;
			SECOND = second;
		}
		
		@Override
		public String toString( ){
			return String.format( "(%s|%s)", FIRST, SECOND );
		}
		
		@Override
		FiniteStateAutomaton generateFSA( ){
			FiniteStateAutomaton first = FIRST.generateFSA();
			FiniteStateAutomaton second = SECOND.generateFSA();
			FiniteStateAutomaton result = new FiniteStateAutomaton( );
			result.INITIAL_STATE.addEpsilonTransitionTo( first.INITIAL_STATE );
			result.INITIAL_STATE.addEpsilonTransitionTo( second.INITIAL_STATE );
			first.ACCEPTING_STATE.addEpsilonTransitionTo( result.ACCEPTING_STATE );
			second.ACCEPTING_STATE.addEpsilonTransitionTo( result.ACCEPTING_STATE );
			return result;
		}
	}
	
	final static class Concatenation extends RegularExpression {
		private final RegularExpression FIRST;
		private final RegularExpression SECOND;
		
		public Concatenation( RegularExpression first, RegularExpression second ){
			super( );
			FIRST = first;
			SECOND = second;
		}
		
		@Override
		public String toString( ){
			return String.format( "(%s.%s)", FIRST, SECOND );
		}
		
		@Override
		FiniteStateAutomaton generateFSA( ){
			FiniteStateAutomaton first = FIRST.generateFSA();
			FiniteStateAutomaton second = SECOND.generateFSA();
			FiniteStateAutomaton result = new FiniteStateAutomaton( );
			result.INITIAL_STATE.addEpsilonTransitionTo( first.INITIAL_STATE );
			first.ACCEPTING_STATE.addEpsilonTransitionTo( second.INITIAL_STATE );
			second.ACCEPTING_STATE.addEpsilonTransitionTo( result.ACCEPTING_STATE );
			return result;
		}
	}
	
	final static class KleeneStar extends RegularExpression {
		private final RegularExpression BASE;
		
		public KleeneStar( RegularExpression base ){
			super( );
			BASE = base;
		}
		
		@Override
		public String toString( ){
			return String.format( "(%s)*", BASE );
		}
		
		@Override
		FiniteStateAutomaton generateFSA( ){
			FiniteStateAutomaton base = BASE.generateFSA();
			FiniteStateAutomaton result = new FiniteStateAutomaton( );
			result.INITIAL_STATE.addEpsilonTransitionTo( base.INITIAL_STATE );
			result.INITIAL_STATE.addEpsilonTransitionTo( result.ACCEPTING_STATE );
			base.ACCEPTING_STATE.addEpsilonTransitionTo( base.INITIAL_STATE );
			base.ACCEPTING_STATE.addEpsilonTransitionTo( result.ACCEPTING_STATE );
			return result;
		}
	}
	
}
