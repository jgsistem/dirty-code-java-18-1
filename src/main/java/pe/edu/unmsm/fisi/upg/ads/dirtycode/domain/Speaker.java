package pe.edu.unmsm.fisi.upg.ads.dirtycode.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions.NoSessionsApprovedException;
import pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions.SpeakerDoesntMeetRequirementsException;

public class Speaker {
	private String firstName;
	private String lastName;
	private String email;
	private int exp;
	private boolean hasBlog;
	private String blogURL;
	private WebBrowser browser;
	private List<String> certifications;
	private String employer;
	private int registrationFee;
	private List<Session> sessions;

	public Integer register(IRepository repository) throws Exception {
		Integer speakerId = null;
		boolean good = false;
		boolean appr = false;
		String[] DatosPersonal = new String[] { "Cobol", "Punch Cards", "Commodore", "VBScript" };
		
		java.io.InputStream is = this.getClass().getResourceAsStream("my.properties");
		java.util.Properties p = new Properties();
		p.load(is);
		
		List<String> domains = Arrays.asList("aol.com", "hotmail.com", "prodigy.com", "compuserve.com");
		
		if (!this.firstName.isEmpty()) {
			if (!this.lastName.isEmpty()) {
				if (!this.email.isEmpty()) {
					
					List<String> emps = Arrays.asList("Pluralsight", "Microsoft", "Google", "Fog Creek Software", "37Signals", "Telerik");
					good = ((this.exp > Integer.parseInt(p.getProperty("Exp1")) || this.hasBlog || this.certifications.size() > Integer.parseInt(p.getProperty("Exp3")) || emps.contains(this.employer)));
					
					if (!good) {
						String[] splitted = this.email.split("@");
						String emailDomain = splitted[splitted.length - 1];
						if (!domains.contains(emailDomain) && (!(browser.getName() == WebBrowser.BrowserName.InternetExplorer && browser.getMajorVersion() < Integer.parseInt(p.getProperty("Exp9")))))
						{
							good = true;
						}
					}
					
					if (good) {
						if (this.sessions.size() != Integer.parseInt(p.getProperty("Exp3"))) {
							for (Session session : sessions) {								
								for (String tech : DatosPersonal) {
									if (session.getTitle().contains(tech) || session.getDescription().contains(tech)) {
										session.setApproved(false);
										break;
									} else {
										session.setApproved(true);
										appr = true;
									}
								}
								
							}
						} else {
							throw new IllegalArgumentException("Can't register speaker with no sessions to present.");
						}
						
						if (appr) {
							if (this.exp <= Integer.parseInt(p.getProperty("Exp1"))) {
								this.registrationFee = Integer.parseInt(p.getProperty("Exp500"));
							}
							else if (exp >= Integer.parseInt(p.getProperty("Exp2")) && exp <= Integer.parseInt(p.getProperty("Exp3"))) {
								this.registrationFee = Integer.parseInt(p.getProperty("Exp250"));
							}
							else if (exp >= Integer.parseInt(p.getProperty("Exp4")) && exp <= Integer.parseInt(p.getProperty("Exp5"))) {
								this.registrationFee = Integer.parseInt(p.getProperty("Exp100"));
							}
							else if (exp >= Integer.parseInt(p.getProperty("Exp6")) && exp <= Integer.parseInt(p.getProperty("Exp9"))) {
								this.registrationFee = Integer.parseInt(p.getProperty("Exp50"));
							}
							else {
								this.registrationFee = Integer.parseInt(p.getProperty("Exp0"));
							}							
							try {
								speakerId = repository.saveSpeaker(this);
							} catch (Exception e) {								
							}
						} else {
							throw new NoSessionsApprovedException("No sessions approved.");
						}
					} else {
						throw new SpeakerDoesntMeetRequirementsException("Speaker doesn't meet our abitrary and capricious standards.");
					}
				} else {
					throw new IllegalArgumentException("Email is required.");
				}				
			} else {
				throw new IllegalArgumentException("Last name is required.");
			}			
		} else {
			throw new IllegalArgumentException("First Name is required");
		}
			
		return speakerId;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public boolean isHasBlog() {
		return hasBlog;
	}

	public void setHasBlog(boolean hasBlog) {
		this.hasBlog = hasBlog;
	}

	public String getBlogURL() {
		return blogURL;
	}

	public void setBlogURL(String blogURL) {
		this.blogURL = blogURL;
	}

	public WebBrowser getBrowser() {
		return browser;
	}

	public void setBrowser(WebBrowser browser) {
		this.browser = browser;
	}

	public List<String> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<String> certifications) {
		this.certifications = certifications;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public int getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(int registrationFee) {
		this.registrationFee = registrationFee;
	}
}