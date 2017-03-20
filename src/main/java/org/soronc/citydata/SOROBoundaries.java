package org.soronc.citydata;

import java.awt.geom.Area;
import java.awt.geom.Path2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SOROBoundaries {
	
	
	private static final Logger log = LoggerFactory.getLogger(SOROBoundaries.class);


	private static Path2D boundary = new Path2D.Double();
	private static Area area;
	static {
		boundary.reset();
		boundary.moveTo(-118.3863,34.0289);
		boundary.lineTo(-118.3841,34.0302);
        boundary.lineTo(-118.3758,34.0323);
        boundary.lineTo(-118.3747,34.0327);
        boundary.lineTo(-118.3770,34.0341);
        boundary.lineTo(-118.3776,34.0345);
        boundary.lineTo(-118.3778,34.0348);
        boundary.lineTo(-118.3778,34.0353);
        boundary.lineTo(-118.3772,34.0382);
        boundary.lineTo(-118.3772,34.0392);
        boundary.lineTo(-118.3763,34.0425);
        boundary.lineTo(-118.3764,34.0431);
        boundary.lineTo(-118.3768,34.0441);
        boundary.lineTo(-118.3768,34.0445);
        boundary.lineTo(-118.3761,34.0470);
        boundary.lineTo(-118.3760,34.0543);
        boundary.lineTo(-118.3761,34.0594);
        boundary.lineTo(-118.3772,34.0593);
        boundary.lineTo(-118.3772,34.0628);
        boundary.lineTo(-118.3772,34.0629);
        boundary.lineTo(-118.3835,34.0629);
        boundary.lineTo(-118.3836,34.0571);
        boundary.lineTo(-118.3966,34.0570);
        boundary.lineTo(-118.3984,34.0570);
        boundary.lineTo(-118.3984,34.0570);
        boundary.lineTo(-118.3991,34.0570);
        boundary.lineTo(-118.3991,34.0571);
        boundary.lineTo(-118.3996,34.0571);
        boundary.lineTo(-118.3996,34.0570);
        boundary.lineTo(-118.4000,34.0568);
        boundary.lineTo(-118.4002,34.0570);
        boundary.lineTo(-118.4004,34.0570);
        boundary.lineTo(-118.4007,34.0568);
        boundary.lineTo(-118.4009,34.0570);
        boundary.lineTo(-118.4007,34.0570);
        boundary.lineTo(-118.4012,34.0570);
        boundary.lineTo(-118.4015,34.0569);
        boundary.lineTo(-118.4016,34.0570);
        boundary.lineTo(-118.4015,34.0570);
        boundary.lineTo(-118.4018,34.0571);
        boundary.lineTo(-118.4022,34.0568);
        boundary.lineTo(-118.4023,34.0569);
        boundary.lineTo(-118.4021,34.0570);
        boundary.lineTo(-118.4024,34.0570);
        boundary.lineTo(-118.4027,34.0569);
        boundary.lineTo(-118.4029,34.0570);
        boundary.lineTo(-118.4032,34.0568);
        boundary.lineTo(-118.4033,34.0569);
        boundary.lineTo(-118.4034,34.0569);
        boundary.lineTo(-118.4034,34.0570);
        boundary.lineTo(-118.4045,34.0570);
        boundary.lineTo(-118.4045,34.0571);
        boundary.lineTo(-118.4058,34.0571);
        boundary.lineTo(-118.4059,34.0547);
        boundary.lineTo(-118.4054,34.0548);
        boundary.lineTo(-118.4052,34.0544);
        boundary.lineTo(-118.4055,34.0543);
        boundary.lineTo(-118.4056,34.0539);
        boundary.lineTo(-118.4060,34.0539);
        boundary.lineTo(-118.4055,34.0528);
        boundary.lineTo(-118.4062,34.0525);
        boundary.lineTo(-118.4106,34.0500);
        boundary.lineTo(-118.4055,34.0437);
        boundary.lineTo(-118.4038,34.0446);
        boundary.lineTo(-118.4037,34.0448);
        boundary.lineTo(-118.4027,34.0453);
        boundary.lineTo(-118.4000,34.0434);
        boundary.lineTo(-118.3999,34.0434);
        boundary.lineTo(-118.3999,34.0432);
        boundary.lineTo(-118.3997,34.0430);
        boundary.lineTo(-118.3993,34.0416);
        boundary.lineTo(-118.3992,34.0412);
        boundary.lineTo(-118.3997,34.0398);
        boundary.lineTo(-118.3997,34.0387);
        boundary.lineTo(-118.3995,34.0377);
        boundary.lineTo(-118.3991,34.0375);
        boundary.lineTo(-118.3995,34.0363);
        boundary.lineTo(-118.3996,34.0362);
        boundary.lineTo(-118.4000,34.0355);
        boundary.lineTo(-118.4015,34.0345);
        boundary.lineTo(-118.4017,34.0343);
        boundary.lineTo(-118.4030,34.0312);
        boundary.lineTo(-118.4029,34.0309);
        boundary.lineTo(-118.4023,34.0304);
        boundary.lineTo(-118.4031,34.0296);
        boundary.lineTo(-118.3985,34.0292);
        boundary.lineTo(-118.3963,34.0290);
        boundary.lineTo(-118.3960,34.0289);
        boundary.lineTo(-118.3956,34.0289);
        boundary.lineTo(-118.3947,34.0289);
        boundary.lineTo(-118.3942,34.0289);
        boundary.lineTo(-118.3931,34.0285);
        boundary.lineTo(-118.3926,34.0284);
        boundary.lineTo(-118.3903,34.0282);
        boundary.lineTo(-118.3900,34.0278);
        boundary.lineTo(-118.3900,34.0277);
        boundary.lineTo(-118.3871,34.0293);
        boundary.lineTo(-118.3867,34.0291);
        boundary.lineTo(-118.3866,34.0292);
        boundary.closePath();
        area = new Area(boundary);
	}
	
	public boolean contains(Location location){
		boolean isInSORO = area.contains(location.getLongitude(), location.getLatitude());
		log.debug("Location {} in SORO? {}", location, isInSORO);
		return isInSORO;
	}
}
