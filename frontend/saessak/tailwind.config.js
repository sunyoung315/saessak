// tailwind.config.js
module.exports = {
	// purge: [],
	purge: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
	darkMode: false, // or 'media' or 'class'
	content: [
		'./node_modules/flowbite/**/*.js',

		'node_modules/flowbite-vue/**/*.{js,jsx,ts,tsx,vue}',
		'node_modules/flowbite/**/*.{js,jsx,ts,tsx}',
	],
	theme: {
		extend: {
			fontFamily: {
				sans: ['omyu_pretty', 'Arial', 'sans-serif'],
			},
			colors: {
				'nav-red': '#D7A2A0',
				'nav-orange': '#E8C8A0',
				'nav-yellow': '#F5E9A8',
				'nav-green': '#A0C09B',
				'nav-blue': '#A2B6CD',
				'nav-navy': '#8B91B9',
				'nav-purple': '#A68BB4',
				'nav-gray' : '#bec1d1',
				'dark-navy': '#333752',
				'yellow-bg1': '#FFFDF4',
				'yellow-bg2': '#FFF4B7',
				'purple-bg': '#DAD2DF',
			},
			backgroundImage: {
				'book-pattern': "url('@/assets/BoardFrame.png')",
			},
			backgroundSize: {
				book: '13.77rem',
			},
		},
	},
	variants: {
		extend: {},
	},
	plugins: [
		require('tailwind-scrollbar-hide', '@tailwindcss/forms', 'flowbite/plugin'),
	],
};
