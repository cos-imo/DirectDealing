package eu.telecomnancy.labfx;

public enum Recurrence {
    Non, Quotidien, Hebdomadaire, Mensuel;

    public static Recurrence getRecurrence(int recurrence) {
        switch (recurrence) {
            case 0:
                return Non;
            case 1:
                return Quotidien;
            case 2:
                return Hebdomadaire;
            case 3:
                return Mensuel;
            default:
                return Non;
        }
    }
    public static int getInt(Recurrence recurrence) {
        switch (recurrence) {
            case Non:
                return 0;
            case Quotidien:
                return 1;
            case Hebdomadaire:
                return 2;
            case Mensuel:
                return 3;
            default:
                return 0;
        }
    }

    public String toString() {
        switch (this) {
            case Non:
                return "Non";
            case Quotidien:
                return "Quotidien";
            case Hebdomadaire:
                return "Hebdomadaire";
            case Mensuel:
                return "Mensuel";
            default:
                return "Non";
        }
    }
}
