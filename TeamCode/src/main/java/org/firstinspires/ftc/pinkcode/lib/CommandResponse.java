package org.firstinspires.ftc.pinkcode.lib;

public enum CommandResponse {
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED");

    String text;

    CommandResponse(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
