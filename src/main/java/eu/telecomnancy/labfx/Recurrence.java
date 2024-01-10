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
}
