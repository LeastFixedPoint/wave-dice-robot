# State management #

Default dice rolling method is stored as a DataDocument attached to the wavelet.

  1. When user clicks on configuration gadget, it sets a property to itself. This fires a DOCUMENT\_CHANGED event.
  1. Robot captures it, reads that gadget property and sets a new DataDocument to wavelet.
  1. When a roll is made, robot retrieves that DataDocument from wave and uses it for rolling.

# Framework #

There are following layers in the implementation:
  * Servlet layer
    * Initializes and configures robot layer.
    * Analyses wave events.
    * Creates configuration gadget.
    * Processes updates from gadget.
    * Asks robot layer to find roll requests and execute them.
    * Provides context information to robot (e.g., default rolling method extracted from wavelet).
    * Flushes output from robot layer to the wave.
    * Handles fatal errors.
  * Robot layer
    * Locates roll requests inside given text and prepares streams for writing their results.
    * Ensures rolling limits.
    * Selects method based on explicit configuration and implicit context passed from servlet layer.
    * Asks method to execute roll.
  * Method layer
    * Parses roll.
    * Executes roll.
    * Writes roll results to output stream passed from robot layer.

# Extensibility #

To be done...