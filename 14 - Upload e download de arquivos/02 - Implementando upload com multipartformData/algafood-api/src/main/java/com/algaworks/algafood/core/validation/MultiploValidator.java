package com.algaworks.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    //O metodo para inicializar a nossa instancia. E ele recebe a instancia da nossa inicializacao
    //senao tivessemos poderia ignorar esse método e apenas trabalhar com isValid()
    @Override
    public void initialize(Multiplo constraintAnnotation) {
        //pego exatemento o numero que foi especificado na nossa anotacao e atribuido a uma variavel de instancia
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;

        if(number != null){
            var valorDecimal = BigDecimal.valueOf(number.doubleValue());
            var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
            var resto = valorDecimal.remainder(multiploDecimal);

            valido = BigDecimal.ZERO.compareTo(resto) == 0;
        }

        return valido;
    }
}
