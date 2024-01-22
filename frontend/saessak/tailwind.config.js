// tailwind.config.js
module.exports = {
	// purge: [],
	purge: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
	darkMode: false, // or 'media' or 'class'
	theme: {
		extend: {
			colors: {
				'nav-red': '#D7A2A0',
				'nav-orange': '#E8C8A0',
				'nav-yellow': '#F5E9A8',
				'nav-green': '#A0C09B',
				'nav-blue': '#A2B6CD',
				'nav-navy': '#8B91B9',
				'nav-purple': '#A68BB4',
				'dark-navy': '#333752',
				'yellow-bg1': '#FFFDF4',
				'yellow-bg2': '#FFF4B7',
			},
		},
	},
	variants: {
		extend: {},
	},
	plugins: [require("tailwind-scrollbar-hide")]
};