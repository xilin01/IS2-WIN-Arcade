module app {
	requires java.sql;
	requires java.desktop;
	requires org.junit.platform.console.standalone;
	requires org.mockito;
	requires org.json;
	opens business.auth;
	opens business.player;
	opens business.user;
	opens business.store;
	opens business.game;
	opens integration.user;
	opens presentation;
}
