package appsettings.subsettings;


import appsettings.FromString;
import appsettings.LocalStorageSetting;

public class Database {
    private static final Database instance = new Database();

    private LocalStorageSetting<String> database;
    private LocalStorageSetting<String> host;
    private LocalStorageSetting<String> port;
    private LocalStorageSetting<String> username;
    private LocalStorageSetting<String> password;

    private Database() {
        database = new LocalStorageSetting<String>(
                "database_credentials_database",
                "",
                FromString.Primitives.STRING);

        host = new LocalStorageSetting<String>(
                "database_credentials_host",
                "",
                FromString.Primitives.STRING);

        port = new LocalStorageSetting<String>(
                "database_credentials_port",
                "",
                FromString.Primitives.STRING);

        username = new LocalStorageSetting<String>(
                "database_credentials_username",
                "",
                FromString.Primitives.STRING);

        password = new LocalStorageSetting<String>(
                "database_credentials_password",
                "",
                FromString.Primitives.STRING);
    }

    public static Database getInstance() {
        return instance;
    }

    public void setAll(
            String database,
            String host,
            String port,
            String username,
            String password) {
        database().set(database);
        host().set(host);
        port().set(port);
        username().set(username);
        password().set(password);
    }

    public LocalStorageSetting<String> database() {
        return database;
    }

    public LocalStorageSetting<String> host() {
        return host;
    }

    public LocalStorageSetting<String> port() {
        return port;
    }

    public LocalStorageSetting<String> username() {
        return username;
    }

    public LocalStorageSetting<String> password() {
        return password;
    }

}
