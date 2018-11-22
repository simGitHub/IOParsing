Explanation of config commands:
#NODES: specify number of nodes in network
SET_BETA_MU/SET_ALPHA_MU: specify mu on beta or alpha (if internal parameters for the memristors (eg. alpha,beta,v_thresh) is not specified, standard values will be used
SET_ALPHA_SIGMA/SET_BETA_SIGMA: 
SET_AMP_VALUE 2: 
ADD_DATASOURCE: specify where the data input voltage nodes will be at with the parameters: nPos,nNeg
SET_VTHRESH: set v_thresh value
BEGIN_MEM/END_MEM: specify that memristors will be added or is done adding, not entirely necessary command
ADD_MEM: add a memristor with the parameters: nPos, nNeg| Rinit, Rmin, Rmax
ADD_MONITOR: specify that voltage monitors will be added (memristor monitors are always added) with the parameters: voltage, all/(or specify the specific node)
BEGIN_NETWORK/END_NETWORK: Specify the start and end of the config file commands, must be added.
ARCH_TYPE: specify if the following network will be set manual (manual using ADD_MEM commands) or a preset, commands parameters is: preset or manual
ARCH_PRESET_TYPE: if preset chosen, then the type of preset must be defined, e.g. "multi:series".
SET_PRESET_SIZE: sets size of the specified preset reservoir type, for "multi_series", the parameter is "depth x length", where depth is 
				 the number of series connections, and length is the length (or number of memristors) in each series connection.
FREQUENCY_INPUT: used to have a frequency based input signal (can be used if one do not wish to use a dataset as voltage source)

commands needed:
Always:
	- BEGIN_NETWORK
	- END_NETWORK
If manual mode:
	- #NODES
	- ADD_DATASOURCE
	- ADD_MEM
if preset mode:
	- SET_PRESET_TYPE
	- SET_PRESET_SIZE
	- BUILD_PRESET_NETWORK # no parameter used in for this command
(#NODES is specified indirectly in SET_PRESET_SIZE)
For preset type 

other: 
	- empty lines are accepted
	- If an unknown command is used, e.g. one has miswritten a command, this will be displayed on program execution
	- if BEGIN_NETWORK/END_NETWORK is missing, the program will not run, and an error will be displayed
	- If one only wants to build a network manually (through using ADD_MEM commands), the preset commands (ARCH_PRESET, ARCH_PRESET_TYPE e.g) is not necessary to be used, but of course the ARCH_PRESET may be used for clarity purpose
	- and other things (perhaps not so important)

output from program:
	- the program writes each memristor monitors, and voltage monitors to a text file (the dictionary must be specified in the java main). So if there are 3 memristor monitors, and 2 voltage monitors, one memristor textfile and one voltage
	  text file will be written, where the memristor textfile will have 3 rows, and voltage textfile 2 rows with datapoints.

(caution, the following explained structure below may be changed, eg. using input when running the program from terminal)
input to the program:
	- the program can take in a text file with values, which will be used as a voltage source. The file needs to be specified in the main (dataName variable). It is assumed that the file name of the text file is in a folder
	  with the same name.  
	 
Types of presets:
	- multi_series
	- crossbar*
*: does not exist yet

explanation of presets:
	- multiseries: basically just multiple series each with their own voltage source connected to them. The series connections are not connected to each other.
	- crossbar: similar to mutli_series, but here the series is connected to each other vertically, and the structure is a bit similar to a crossbar structure
		(find better word similarity instead of crossbar?)
	


