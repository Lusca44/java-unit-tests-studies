package br.ce.wcaquino.servicos.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.wcaquino.servicos.CalculadoraTDDTest;
import br.ce.wcaquino.servicos.CalculoValorLocacaoTest;
import br.ce.wcaquino.servicos.LocacaoServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	LocacaoServiceTest.class,
	CalculoValorLocacaoTest.class,
	CalculadoraTDDTest.class
})
public class SuitesExec {

}
