from scapy.all import *

def changeload(packet, load):
	
	p = packet
	p.load = load

	#Delete the chksums and lengths so they can be recalculated
	del p[IP].chksum
	del p[IP].len
	del p[UDP].chksum
	del p[UDP].len

	p.show2()

	return p