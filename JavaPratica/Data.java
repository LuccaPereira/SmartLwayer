public class Data
{
    private byte  dia, mes;
    private short ano;

    public static boolean isBissexto (short ano)
    {
        // Calendario Juliano
        if (ano<1582)
            if (ano%4==0)
                return true;
            else
                return false;

        // Calendario Gregoriano
        if (ano%400==0) return true;
        if (ano%  4==0 && ano%100!=0) return true;
        return false;
    }

    public static boolean isValida (byte dia, byte mes, short ano)
    {
        if (ano<-45) return false; // antes do Calendario Juliano
        if (ano== 0) return false; // nao existiu ano 0; do ano 1ac foi direto para o ano 1dc
        if (ano==1582 && mes==10 && dia>=5 && dia<=14) return false; // dias cortados dos calendario pelo Papa Gregorio

        if (dia<1 || dia>31 || mes<1 || mes>12) return false;

        if (dia>30 && (mes==4 || mes==6 || mes==9 || mes==11)) return false;
        if (dia>29 && mes==2) return false;
        if (dia>28 && mes==2 && !Data.isBissexto(ano)) return false;

        return true;
    }

    public /*void*/ Data (byte dia, byte mes, short ano) throws Exception
    {
        if (!Data.isValida(dia,mes,ano))
            throw new Exception ("Data invalida");

        this.dia=dia;
        this.mes=mes;
        this.ano=ano;
    }

    public void setDia (byte dia) throws Exception
    {
        if (!Data.isValida(dia,this.mes,this.ano))
            throw new Exception ("Dia invalido");

        this.dia=dia;
    }

    public byte getDia ()
    {
        return this.dia;
    }
    
    public void setMes (byte mes) throws Exception
    {
        if (!Data.isValida(this.dia,mes,this.ano))
            throw new Exception ("Mes invalido");

        this.mes=mes;
    }

    public byte getMes ()
    {
        return this.mes;
    }
    
    public void setAno (short ano) throws Exception
    {
        if (!Data.isValida(this.dia,this.mes,ano))
            throw new Exception ("Ano invalido");

        this.ano=ano;
    }

    public short getAno ()
    {
        return this.ano;
    }
    
    public void avanceUmDia() {
        int novoDia = this.dia + 1;
        int mesAtual = this.mes;
        if (mesAtual == 1 || mesAtual == 3 || mesAtual == 5 || mesAtual == 7 || mesAtual == 8 || mesAtual == 10 || mesAtual == 12) {
            if (novoDia > 31) {
                this.dia = 1;
                this.mes++;
                if (this.mes > 12) {
                    this.mes = 1;
                    this.ano++;
                }
            } else {
                this.dia++;
            }
        } else if (mesAtual == 4 || mesAtual == 6 || mesAtual == 9 || mesAtual == 11) {
            if (novoDia > 30) {
                this.dia = 1;
                this.mes++;
            } else {
                this.dia++;
            }
        } else if (mesAtual == 2) {
            if (isBissexto(this.ano)) {
                if (novoDia > 29) {
                    this.dia = 1;
                    this.mes++;
                } else {
                    this.dia++;
                }
            } else {
                if (novoDia > 28) {
                    this.dia = 1;
                    this.mes++;
                } else {
                    this.dia++;
                }
            }
        }
    }

    public Data getDiaSeguinte () // não altera o this
    {
        try {
            Data copia = new Data(this.dia, this.mes, this.ano);
            copia.avanceUmDia();
            return copia;
        } catch (Exception erro) {
            return null;
        }
    }
}
