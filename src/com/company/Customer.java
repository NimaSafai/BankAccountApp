package com.company;

public class Customer {
    private final String name;
    private final Personnummer personnummer;

    public Customer(String name, String personnummer) {
        this.name = name;
        this.personnummer = new Personnummer(personnummer);
    }

    public String getName() {
        return name;
    }

    public Personnummer getPersonnummer() {
        return personnummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return getPersonnummer() != null ? getPersonnummer().equals(customer.getPersonnummer()) : customer.getPersonnummer() == null;
    }

    @Override
    public int hashCode() {
        return getPersonnummer() != null ? getPersonnummer().hashCode() : 0;
    }

    static class Personnummer {
        private final String personnummer;

        public Personnummer(String personnummer) {
            this.personnummer = convertToPersonnummerFormat(personnummer);
        }

        static String convertToPersonnummerFormat(String input) {
            return input.trim().replaceAll("\\D", "");
        }

        public String getPersonnummer() {
            return personnummer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (o.getClass() == String.class) {
                o = Personnummer.convertToPersonnummerFormat(((String) o));
                return this.getPersonnummer().equals(o);
            } else if (this.getClass() == o.getClass()) {
                Personnummer that = (Personnummer) o;

                return getPersonnummer() != null ? getPersonnummer().equals(that.getPersonnummer()) : that.getPersonnummer() == null;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return getPersonnummer() != null ? getPersonnummer().hashCode() : 0;
        }

        @Override
        public String toString() {
            return this.personnummer;
        }
    }
}
