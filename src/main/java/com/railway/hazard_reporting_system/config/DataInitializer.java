//package com.railway.hazard_reporting_system.config;
//
//import com.railway.hazard_reporting_system.entity.core.*;
//import com.railway.hazard_reporting_system.entity.enums.*;
//import com.railway.hazard_reporting_system.repository.RailroadCompanyRepository;
//import com.railway.hazard_reporting_system.repository.RailSegmentRepository;
//import com.railway.hazard_reporting_system.repository.UserRepository;
//import com.railway.hazard_reporting_system.util.GeometryHelper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    @Autowired
//    private RailroadCompanyRepository railroadCompanyRepository;
//
//    @Autowired
//    private RailSegmentRepository railSegmentRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        initializeRailroadCompanies();
//        initializeRailSegments();
//        initializeSampleUsers();
//    }
//
//    private void initializeRailroadCompanies() {
//        if (railroadCompanyRepository.count() == 0) {
//            System.out.println("Initializing Railroad Companies...");
//
//            // BNSF Railway
//            RailroadCompany bnsf = new RailroadCompany("BNSF Railway", "BNSF");
//            bnsf.setDescription("Burlington Northern Santa Fe Railway - Major freight railroad");
//            bnsf.setHeadquartersLocation("Fort Worth, Texas");
//            bnsf.setContactEmail("dispatch@bnsf.com");
//            bnsf.setContactPhone("1-800-832-5452");
//            bnsf.setEmergencyContact("1-800-832-5452");
//            bnsf.setWebsiteUrl("https://www.bnsf.com");
//            railroadCompanyRepository.save(bnsf);
//
//            // Union Pacific
//            RailroadCompany up = new RailroadCompany("Union Pacific Railroad", "UP");
//            up.setDescription("Union Pacific - Major Class I freight railroad");
//            up.setHeadquartersLocation("Omaha, Nebraska");
//            up.setContactEmail("dispatch@up.com");
//            up.setContactPhone("1-888-877-7267");
//            up.setEmergencyContact("1-888-877-7267");
//            up.setWebsiteUrl("https://www.up.com");
//            railroadCompanyRepository.save(up);
//
//            // CSX Transportation
//            RailroadCompany csx = new RailroadCompany("CSX Transportation", "CSX");
//            csx.setDescription("CSX Transportation - Major Class I freight railroad serving Eastern US");
//            csx.setHeadquartersLocation("Jacksonville, Florida");
//            csx.setContactEmail("dispatch@csx.com");
//            csx.setContactPhone("1-877-835-5279");
//            csx.setEmergencyContact("1-800-232-0144");
//            csx.setWebsiteUrl("https://www.csx.com");
//            railroadCompanyRepository.save(csx);
//
//            // Norfolk Southern
//            RailroadCompany ns = new RailroadCompany("Norfolk Southern Railway", "NS");
//            ns.setDescription("Norfolk Southern - Class I freight railroad serving Eastern US");
//            ns.setHeadquartersLocation("Atlanta, Georgia");
//            ns.setContactEmail("dispatch@nscorp.com");
//            ns.setContactPhone("1-800-453-2530");
//            ns.setEmergencyContact("1-800-453-2530");
//            ns.setWebsiteUrl("https://www.nscorp.com");
//            railroadCompanyRepository.save(ns);
//
//            // Sound Transit (operates on BNSF tracks but worth noting for commuter service)
//            RailroadCompany soundTransit = new RailroadCompany("Sound Transit", "ST");
//            soundTransit.setDescription("Regional transit authority operating commuter rail on BNSF-owned tracks");
//            soundTransit.setHeadquartersLocation("Seattle, Washington");
//            soundTransit.setContactEmail("customerservice@soundtransit.org");
//            soundTransit.setContactPhone("1-888-889-6368");
//            soundTransit.setWebsiteUrl("https://www.soundtransit.org");
//            railroadCompanyRepository.save(soundTransit);
//
//            // Seattle Terminal Railways
//            RailroadCompany seattleTerminal = new RailroadCompany("Port of Seattle", "POS");
//            seattleTerminal.setDescription("Port of Seattle terminal railway operations");
//            seattleTerminal.setHeadquartersLocation("Seattle, Washington");
//            seattleTerminal.setContactEmail("portinfo@portseattle.org");
//            seattleTerminal.setContactPhone("206-787-3000");
//            seattleTerminal.setWebsiteUrl("https://www.portseattle.org");
//            railroadCompanyRepository.save(seattleTerminal);
//
//            // Tacoma Rail
//            RailroadCompany tacomaRail = new RailroadCompany("Tacoma Rail", "TMBL");
//            tacomaRail.setDescription("City of Tacoma municipal railroad");
//            tacomaRail.setHeadquartersLocation("Tacoma, Washington");
//            tacomaRail.setContactEmail("rail@cityoftacoma.org");
//            tacomaRail.setContactPhone("253-502-8700");
//            tacomaRail.setWebsiteUrl("https://www.tacomarail.com");
//            railroadCompanyRepository.save(tacomaRail);
//
//            System.out.println("Railroad Companies initialized successfully!");
//        }
//    }
//
//
//    private void initializeRailSegments() {
//        if (railSegmentRepository.count() == 0) {
//            System.out.println("Initializing Rail Segments...");
//
//            RailroadCompany bnsf = railroadCompanyRepository.findByCode("BNSF").orElseThrow();
//            RailroadCompany up = railroadCompanyRepository.findByCode("UP").orElseThrow();
//            RailroadCompany csx = railroadCompanyRepository.findByCode("CSX").orElseThrow();
//            RailroadCompany ns = railroadCompanyRepository.findByCode("NS").orElseThrow();
//            RailroadCompany soundTransit = railroadCompanyRepository.findByCode("ST").orElseThrow();
//            RailroadCompany portSeattle = railroadCompanyRepository.findByCode("POS").orElseThrow();
//            RailroadCompany tacomaRail = railroadCompanyRepository.findByCode("TMBL").orElseThrow();
//
//            // BNSF Segments
//            createRailSegment("BNSF Transcon - Chicago to Los Angeles", "BNSF-TC-001",
//                    "Chicago, IL", "Los Angeles, CA", 2256.0, 70, TrackType.DOUBLE_TRACK,
//                    "Transcon", "Chicago Division", bnsf,
//                    41.8781, -87.6298, 34.0522, -118.2437); // Chicago to LA coords
//
//            createRailSegment("BNSF Northern Transcon - Seattle to Chicago", "BNSF-NT-002",
//                    "Seattle, WA", "Chicago, IL", 2206.0, 70, TrackType.DOUBLE_TRACK,
//                    "Northern Transcon", "Seattle Division", bnsf,
//                    47.6062, -122.3321, 41.8781, -87.6298); // Seattle to Chicago coords
//
//            createRailSegment("BNSF Powder River Basin", "BNSF-PRB-003",
//                    "Gillette, WY", "Alliance, NE", 350.0, 60, TrackType.DOUBLE_TRACK,
//                    "Powder River Basin", "Wyoming Division", bnsf,
//                    44.2911, -105.5022, 42.0975, -102.8721); // Gillette to Alliance coords
//
//            createRailSegment("BNSF Denver to Alliance", "BNSF-DA-004",
//                    "Denver, CO", "Alliance, NE", 289.0, 60, TrackType.SINGLE_TRACK,
//                    "Denver Sub", "Colorado Division", bnsf,
//                    39.7392, -104.9903, 42.0975, -102.8721);
//
//            createRailSegment("BNSF Galesburg to Kansas City", "BNSF-GKC-005",
//                    "Galesburg, IL", "Kansas City, MO", 262.0, 60, TrackType.DOUBLE_TRACK,
//                    "Hannibal Sub", "Illinois Division", bnsf,
//                    40.9478, -90.3712, 39.0997, -94.5786);
//
//            createRailSegment("BNSF Bakersfield to Stockton", "BNSF-BS-006",
//                    "Bakersfield, CA", "Stockton, CA", 283.0, 70, TrackType.SINGLE_TRACK,
//                    "Bakersfield Sub", "California Division", bnsf,
//                    35.3733, -119.0187, 37.9577, -121.2908);
//
//            createRailSegment("BNSF Memphis to Birmingham", "BNSF-MB-007",
//                    "Memphis, TN", "Birmingham, AL", 249.0, 60, TrackType.SINGLE_TRACK,
//                    "Memphis Sub", "Alabama Division", bnsf,
//                    35.1495, -90.0490, 33.5207, -86.8025);
//
//            createRailSegment("BNSF Fort Worth to Galveston", "BNSF-FG-008",
//                    "Fort Worth, TX", "Galveston, TX", 315.0, 60, TrackType.SINGLE_TRACK,
//                    "Galveston Sub", "Texas Division", bnsf,
//                    32.7555, -97.3308, 29.3013, -94.7977);
//
//            // BNSF Seattle/Puget Sound Area Segments (Major hub!)
//            createRailSegment("BNSF Seattle Subdivision - Seattle to Everett", "BNSF-SEA-009",
//                    "Seattle, WA", "Everett, WA", 28.0, 60, TrackType.DOUBLE_TRACK,
//                    "Seattle Sub", "Puget Sound Division", bnsf,
//                    47.6062, -122.3321, 47.9790, -122.2021);
//
//            createRailSegment("BNSF Scenic Subdivision - Everett to Wenatchee", "BNSF-SCN-010",
//                    "Everett, WA", "Wenatchee, WA", 150.0, 60, TrackType.SINGLE_TRACK,
//                    "Scenic Sub", "Washington Division", bnsf,
//                    47.9790, -122.2021, 47.4235, -120.3103);
//
//            createRailSegment("BNSF Vancouver Subdivision - Seattle to Vancouver BC", "BNSF-VAN-011",
//                    "Seattle, WA", "Blaine, WA", 120.0, 79, TrackType.DOUBLE_TRACK,
//                    "Vancouver Sub", "Puget Sound Division", bnsf,
//                    47.6062, -122.3321, 48.9936, -122.7473);
//
//            createRailSegment("BNSF Edmonds Waterfront Line", "BNSF-EDM-012",
//                    "Edmonds, WA", "Mukilteo, WA", 8.5, 40, TrackType.DOUBLE_TRACK,
//                    "Seattle Sub", "Puget Sound Division", bnsf,
//                    47.8107, -122.3774, 47.9445, -122.3046);
//
//            createRailSegment("BNSF Bellingham Subdivision", "BNSF-BEL-013",
//                    "Everett, WA", "Bellingham, WA", 85.0, 60, TrackType.DOUBLE_TRACK,
//                    "Bellingham Sub", "Puget Sound Division", bnsf,
//                    47.9790, -122.2021, 48.7519, -122.4787);
//
//            createRailSegment("BNSF Stampede Pass - Auburn to Ellensburg", "BNSF-STP-014",
//                    "Auburn, WA", "Ellensburg, WA", 95.0, 50, TrackType.SINGLE_TRACK,
//                    "Stampede Sub", "Washington Division", bnsf,
//                    47.3073, -122.2285, 46.9965, -120.5478);
//
//            createRailSegment("BNSF Columbia River Gorge - Vancouver to Pasco", "BNSF-CRG-015",
//                    "Vancouver, WA", "Pasco, WA", 215.0, 60, TrackType.DOUBLE_TRACK,
//                    "Fallbridge Sub", "Washington Division", bnsf,
//                    45.6387, -122.6615, 46.2396, -119.1006);
//
//            createRailSegment("BNSF Lakewood Subdivision - Tacoma to Centralia", "BNSF-LKW-016",
//                    "Tacoma, WA", "Centralia, WA", 65.0, 60, TrackType.DOUBLE_TRACK,
//                    "Lakewood Sub", "Puget Sound Division", bnsf,
//                    47.2529, -122.4443, 46.7162, -122.9543);
//
//            createRailSegment("BNSF Seattle Harbor - Interbay Yard to Port of Seattle", "BNSF-SHB-017",
//                    "Interbay, Seattle", "Harbor Island, Seattle", 5.2, 25, TrackType.MULTIPLE_TRACK,
//                    "Seattle Harbor", "Puget Sound Division", bnsf,
//                    47.6470, -122.3750, 47.5739, -122.3539);
//
//            createRailSegment("BNSF New Castle Subdivision - Renton to Black Diamond", "BNSF-NCL-018",
//                    "Renton, WA", "Black Diamond, WA", 22.0, 40, TrackType.SINGLE_TRACK,
//                    "New Castle Sub", "Puget Sound Division", bnsf,
//                    47.4829, -122.2171, 47.3089, -122.0032);
//
//            createRailSegment("BNSF Woodinville Subdivision - Seattle to Snohomish", "BNSF-WDV-019",
//                    "Seattle, WA", "Snohomish, WA", 42.0, 40, TrackType.SINGLE_TRACK,
//                    "Woodinville Sub", "Puget Sound Division", bnsf,
//                    47.6629, -122.3186, 47.9129, -122.0982);
//
//            // Union Pacific Segments
//            createRailSegment("UP Overland Route - Oakland to Chicago", "UP-OR-001",
//                    "Oakland, CA", "Chicago, IL", 2273.0, 70, TrackType.DOUBLE_TRACK,
//                    "Overland Route", "Western Region", up,
//                    37.8044, -122.2712, 41.8781, -87.6298);
//
//            createRailSegment("UP Sunset Route - Los Angeles to New Orleans", "UP-SR-002",
//                    "Los Angeles, CA", "New Orleans, LA", 1995.0, 70, TrackType.SINGLE_TRACK,
//                    "Sunset Route", "Southern Region", up,
//                    34.0522, -118.2437, 29.9511, -90.0715);
//
//            createRailSegment("UP Central Corridor - Kansas City to Denver", "UP-CC-003",
//                    "Kansas City, MO", "Denver, CO", 640.0, 70, TrackType.DOUBLE_TRACK,
//                    "Kansas Sub", "Central Region", up,
//                    39.0997, -94.5786, 39.7392, -104.9903);
//
//            createRailSegment("UP North Platte to Cheyenne", "UP-NPC-004",
//                    "North Platte, NE", "Cheyenne, WY", 279.0, 70, TrackType.DOUBLE_TRACK,
//                    "Sidney Sub", "Western Region", up,
//                    41.1239, -100.7654, 41.1400, -104.8202);
//
//            createRailSegment("UP Portland to Seattle", "UP-PS-005",
//                    "Portland, OR", "Seattle, WA", 186.0, 60, TrackType.SINGLE_TRACK,
//                    "Brooklyn Sub", "Western Region", up,
//                    45.5152, -122.6784, 47.6062, -122.3321);
//
//            // Union Pacific Seattle/Tacoma Area
//            createRailSegment("UP Seattle Subdivision - Seattle to Tacoma", "UP-SEA-020",
//                    "Seattle, WA", "Tacoma, WA", 32.0, 50, TrackType.DOUBLE_TRACK,
//                    "Seattle Sub", "Western Region", up,
//                    47.5952, -122.3316, 47.2529, -122.4443);
//
//            createRailSegment("UP Tacoma to Olympia", "UP-OLY-021",
//                    "Tacoma, WA", "Olympia, WA", 31.0, 50, TrackType.SINGLE_TRACK,
//                    "Olympia Sub", "Western Region", up,
//                    47.2529, -122.4443, 47.0379, -122.9007);
//
//            createRailSegment("UP East Portland to Vancouver WA", "UP-VAN-022",
//                    "Portland, OR", "Vancouver, WA", 12.0, 40, TrackType.DOUBLE_TRACK,
//                    "Vancouver Sub", "Western Region", up,
//                    45.5152, -122.6784, 45.6387, -122.6615);
//
//            createRailSegment("UP Argo Yard to Boeing Renton", "UP-ARG-023",
//                    "Argo Yard, Seattle", "Renton, WA", 14.5, 25, TrackType.SINGLE_TRACK,
//                    "Argo Industrial", "Western Region", up,
//                    47.5447, -122.3320, 47.4829, -122.2171);
//
//            createRailSegment("UP Houston to San Antonio", "UP-HSA-006",
//                    "Houston, TX", "San Antonio, TX", 197.0, 60, TrackType.SINGLE_TRACK,
//                    "Houston Sub", "Southern Region", up,
//                    29.7604, -95.3698, 29.4241, -98.4936);
//
//            createRailSegment("UP Salt Lake City to Las Vegas", "UP-SLV-007",
//                    "Salt Lake City, UT", "Las Vegas, NV", 420.0, 70, TrackType.SINGLE_TRACK,
//                    "Sharp Sub", "Western Region", up,
//                    40.7608, -111.8910, 36.1699, -115.1398);
//
//            createRailSegment("UP Omaha to Council Bluffs", "UP-OCB-008",
//                    "Omaha, NE", "Council Bluffs, IA", 8.5, 25, TrackType.DOUBLE_TRACK,
//                    "Council Bluffs Sub", "Central Region", up,
//                    41.2565, -95.9345, 41.2619, -95.8608);
//
//            // CSX Segments
//            createRailSegment("CSX I-95 Corridor - Richmond to Jacksonville", "CSX-I95-001",
//                    "Richmond, VA", "Jacksonville, FL", 550.0, 70, TrackType.DOUBLE_TRACK,
//                    "A-Line", "Florence Division", csx,
//                    37.5407, -77.4360, 30.3322, -81.6557);
//
//            createRailSegment("CSX Water Level Route - New York to Chicago", "CSX-WLR-002",
//                    "New York, NY", "Chicago, IL", 960.0, 79, TrackType.DOUBLE_TRACK,
//                    "Chicago Line", "Great Lakes Division", csx,
//                    40.7128, -74.0060, 41.8781, -87.6298);
//
//            createRailSegment("CSX Big Sandy Subdivision", "CSX-BS-003",
//                    "Russell, KY", "Shelby, KY", 95.0, 50, TrackType.SINGLE_TRACK,
//                    "Big Sandy Sub", "Louisville Division", csx,
//                    38.5170, -82.6971, 37.7798, -85.2239);
//
//            createRailSegment("CSX Baltimore to Philadelphia", "CSX-BP-004",
//                    "Baltimore, MD", "Philadelphia, PA", 102.0, 70, TrackType.DOUBLE_TRACK,
//                    "Philadelphia Sub", "Baltimore Division", csx,
//                    39.2904, -76.6122, 39.9526, -75.1652);
//
//            createRailSegment("CSX Atlanta to Macon", "CSX-AM-005",
//                    "Atlanta, GA", "Macon, GA", 103.0, 60, TrackType.DOUBLE_TRACK,
//                    "Georgia Road Sub", "Atlanta Division", csx,
//                    33.7490, -84.3880, 32.8407, -83.6324);
//
//            createRailSegment("CSX Cumberland to Martinsburg", "CSX-CM-006",
//                    "Cumberland, MD", "Martinsburg, WV", 38.0, 50, TrackType.DOUBLE_TRACK,
//                    "Mountain Sub", "Baltimore Division", csx,
//                    39.6529, -78.7625, 39.4562, -77.9639);
//
//            createRailSegment("CSX Tampa to Orlando", "CSX-TO-007",
//                    "Tampa, FL", "Orlando, FL", 85.0, 60, TrackType.SINGLE_TRACK,
//                    "Tampa Sub", "Jacksonville Division", csx,
//                    27.9506, -82.4572, 28.5383, -81.3792);
//
//            // Norfolk Southern Segments
//            createRailSegment("NS Crescent Corridor - New York to New Orleans", "NS-CC-001",
//                    "New York, NY", "New Orleans, LA", 1377.0, 70, TrackType.DOUBLE_TRACK,
//                    "Crescent Corridor", "Eastern Region", ns,
//                    40.7128, -74.0060, 29.9511, -90.0715);
//
//            createRailSegment("NS Heartland Corridor - Norfolk to Chicago", "NS-HC-002",
//                    "Norfolk, VA", "Chicago, IL", 570.0, 70, TrackType.DOUBLE_TRACK,
//                    "Heartland Corridor", "Northern Region", ns,
//                    36.8508, -76.2859, 41.8781, -87.6298);
//
//            createRailSegment("NS Piedmont Division - Charlotte to Danville", "NS-PD-003",
//                    "Charlotte, NC", "Danville, VA", 92.0, 60, TrackType.DOUBLE_TRACK,
//                    "Piedmont District", "Piedmont Division", ns,
//                    35.2271, -80.8431, 36.5860, -79.3950);
//
//            createRailSegment("NS Birmingham to Meridian", "NS-BM-004",
//                    "Birmingham, AL", "Meridian, MS", 150.0, 60, TrackType.SINGLE_TRACK,
//                    "AGS South District", "Alabama Division", ns,
//                    33.5207, -86.8025, 32.3643, -88.7034);
//
//            createRailSegment("NS Pocahontas Division - Norfolk to Bluefield", "NS-POC-005",
//                    "Norfolk, VA", "Bluefield, WV", 300.0, 50, TrackType.DOUBLE_TRACK,
//                    "Pocahontas District", "Pocahontas Division", ns,
//                    36.8508, -76.2859, 37.2698, -81.2223);
//
//            createRailSegment("NS Cincinnati to Louisville", "NS-CL-006",
//                    "Cincinnati, OH", "Louisville, KY", 108.0, 60, TrackType.DOUBLE_TRACK,
//                    "CNO&TP District", "Kentucky Division", ns,
//                    39.1031, -84.5120, 38.2527, -85.7585);
//
//            // Sound Transit Sounder Commuter Rail (runs on BNSF tracks)
//            createRailSegment("Sounder North Line - Seattle to Everett", "ST-NTH-001",
//                    "King Street Station, Seattle", "Everett Station", 28.0, 79, TrackType.DOUBLE_TRACK,
//                    "North Line", "Sounder Service", soundTransit,
//                    47.5990, -122.3301, 47.9754, -122.1977);
//
//            createRailSegment("Sounder South Line - Seattle to Tacoma", "ST-STH-001",
//                    "King Street Station, Seattle", "Tacoma Dome Station", 35.0, 79, TrackType.DOUBLE_TRACK,
//                    "South Line", "Sounder Service", soundTransit,
//                    47.5990, -122.3301, 47.2428, -122.4280);
//
//            createRailSegment("Sounder South Line - Tacoma to Lakewood", "ST-STH-002",
//                    "Tacoma Dome Station", "Lakewood Station", 12.0, 79, TrackType.DOUBLE_TRACK,
//                    "South Line Extension", "Sounder Service", soundTransit,
//                    47.2428, -122.4280, 47.1717, -122.5182);
//
//            // Port of Seattle Terminal Railways
//            createRailSegment("Port of Seattle Terminal 5", "POS-T5-001",
//                    "Interbay Junction", "Terminal 5", 3.5, 15, TrackType.YARD_TRACK,
//                    "Terminal 5", "Harbor Operations", portSeattle,
//                    47.6470, -122.3750, 47.5839, -122.3622);
//
//            createRailSegment("Port of Seattle Terminal 18", "POS-T18-001",
//                    "Harbor Island Junction", "Terminal 18", 2.8, 15, TrackType.YARD_TRACK,
//                    "Terminal 18", "Harbor Operations", portSeattle,
//                    47.5739, -122.3539, 47.5649, -122.3489);
//
//            createRailSegment("Port of Seattle Terminal 46", "POS-T46-001",
//                    "East Marginal Way", "Terminal 46", 2.2, 15, TrackType.YARD_TRACK,
//                    "Terminal 46", "Harbor Operations", portSeattle,
//                    47.5739, -122.3539, 47.5572, -122.3380);
//
//            // Tacoma Rail
//            createRailSegment("Tacoma Rail Mountain Division - Tacoma to Morton", "TMBL-MTN-001",
//                    "Tacoma, WA", "Morton, WA", 88.0, 25, TrackType.SINGLE_TRACK,
//                    "Mountain Division", "Tacoma Operations", tacomaRail,
//                    47.2529, -122.4443, 46.5581, -122.2771);
//
//            createRailSegment("Tacoma Rail Tideflats Industrial", "TMBL-TID-001",
//                    "Tacoma Yard", "Port of Tacoma", 8.5, 15, TrackType.INDUSTRIAL_TRACK,
//                    "Tideflats", "Port Operations", tacomaRail,
//                    47.2448, -122.4381, 47.2681, -122.4231);
//
//            createRailSegment("Tacoma Rail East End - Tacoma to Frederickson", "TMBL-EE-001",
//                    "Tacoma, WA", "Frederickson, WA", 15.0, 25, TrackType.SINGLE_TRACK,
//                    "East End", "Tacoma Operations", tacomaRail,
//                    47.2529, -122.4443, 47.0909, -122.3593);
//
//            System.out.println("Rail Segments initialized successfully!");
//        }
//    }
//
//
//    private void createRailSegment(String name, String code, String startLocation,
//                                   String endLocation, Double lengthMiles, Integer maxSpeed,
//                                   TrackType trackType, String subdivision, String territory,
//                                   RailroadCompany company, double startLat, double startLon,
//                                   double endLat, double endLon) {
//        RailSegment segment = new RailSegment(name, company);
//        segment.setSegmentCode(code);
//        segment.setStartLocation(startLocation);
//        segment.setEndLocation(endLocation);
//        segment.setLengthMiles(lengthMiles);
//        segment.setMaxSpeedMph(maxSpeed);
//        segment.setTrackType(trackType);
//        segment.setSubdivision(subdivision);
//        segment.setTerritory(territory);
//        //segment.setStatus(SegmentStatus.ACTIVE);
//
//        // Add description
//        segment.setDescription(String.format("%s main line segment operated by %s. " +
//                        "Connects %s to %s spanning %.1f miles with maximum authorized speed of %d mph.",
//                trackType.getDisplayName(), company.getName(), startLocation, endLocation,
//                lengthMiles, maxSpeed));
//
//        // Add milepost data (start at 0, end at length)
//        segment.setStartMilepost(0.0);
//        segment.setEndMilepost(lengthMiles);
//
//        // Create simplified LineString geometry (start and end points only)
//        // In production, this would have many waypoints along the actual route
//        segment.setGeometry(GeometryHelper.createSimpleLineString(startLat, startLon, endLat, endLon));
//
//        railSegmentRepository.save(segment);
//    }
//
//    private void initializeSampleUsers() {
//        if (userRepository.count() == 0) {
//            System.out.println("Initializing Sample Users...");
//
//            RailroadCompany bnsf = railroadCompanyRepository.findByCode("BNSF").orElseThrow();
//            RailroadCompany up = railroadCompanyRepository.findByCode("UP").orElseThrow();
//            RailroadCompany csx = railroadCompanyRepository.findByCode("CSX").orElseThrow();
//
//            // System Admin
//            User admin = new User("admin", "admin@railway.com", "password123");
//            admin.setFirstName("System");
//            admin.setLastName("Administrator");
//            admin.setRole(UserRole.ADMIN);
//            userRepository.save(admin);
//
//            // BNSF Dispatcher
//            User bnsfdispatcher = new User("bnsf_dispatch", "dispatch@bnsf.com", "password123");
//            bnsfdispatcher.setFirstName("John");
//            bnsfdispatcher.setLastName("Smith");
//            bnsfdispatcher.setRole(UserRole.DISPATCHER);
//            bnsfdispatcher.setRailroadCompany(bnsf);
//            bnsfdispatcher.setPhoneNumber("817-555-0123");
//            userRepository.save(bnsfdispatcher);
//
//            // UP Inspector
//            User upinspector = new User("up_inspector", "inspector@up.com", "password123");
//            upinspector.setFirstName("Sarah");
//            upinspector.setLastName("Johnson");
//            upinspector.setRole(UserRole.INSPECTOR);
//            upinspector.setRailroadCompany(up);
//            upinspector.setPhoneNumber("402-555-0456");
//            userRepository.save(upinspector);
//
//            // CSX Reporter
//            User csxreporter = new User("csx_reporter", "reporter@csx.com", "password123");
//            csxreporter.setFirstName("Mike");
//            csxreporter.setLastName("Williams");
//            csxreporter.setRole(UserRole.REPORTER);
//            csxreporter.setRailroadCompany(csx);
//            csxreporter.setPhoneNumber("904-555-0789");
//            userRepository.save(csxreporter);
//
//            // General Reporter (not affiliated with specific railroad)
//            User generalreporter = new User("reporter1", "reporter@example.com", "password123");
//            generalreporter.setFirstName("Jane");
//            generalreporter.setLastName("Doe");
//            generalreporter.setRole(UserRole.REPORTER);
//            generalreporter.setPhoneNumber("555-555-5555");
//            userRepository.save(generalreporter);
//
//            System.out.println("Sample Users initialized successfully!");
//        }
//    }
//}
